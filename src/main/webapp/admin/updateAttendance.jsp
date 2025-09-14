<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.sql.*, java.util.*, com.DB.DBConnect, com.entity.Attendance, com.DAO.AttendanceDAOImpl"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>

<%
if (session == null || session.getAttribute("userType") == null
		|| !"Admin".equals(session.getAttribute("userType"))) {
	response.sendRedirect("../login.jsp");
	return;
}

String selectedStandard = request.getParameter("standard");
String selectedDate = request.getParameter("date");

List<Attendance> attendanceList = new ArrayList<>();

if (selectedStandard != null && selectedDate != null) {
	AttendanceDAOImpl attendanceDAO = new AttendanceDAOImpl();
	attendanceList = attendanceDAO.getAttendanceByStandardAndDate(
	Integer.parseInt(selectedStandard), selectedDate);
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: Update Attendance</title>
<%@include file="/all_components/allCSS.css"%>
</head>
<body>

	<%@include file="/all_components/sidebar.jsp"%>

	<div class="content">
		<%@include file="/all_components/navbar.jsp"%>
		<%
		String successMsg = (String) session.getAttribute("successMsg");
		String infoMsg = (String) session.getAttribute("infoMsg");
		String errorMsg = (String) session.getAttribute("errorMsg");

		if (successMsg != null) {
		%>
		<div class="alert alert-success"><%=successMsg%></div>
		<%
		session.removeAttribute("successMsg");
		}

		if (infoMsg != null) {
		%>
		<div class="alert alert-info"><%=infoMsg%></div>
		<%
		session.removeAttribute("infoMsg");
		}

		if (errorMsg != null) {
		%>
		<div class="alert alert-danger"><%=errorMsg%></div>
		<%
		session.removeAttribute("errorMsg");
		}
		%>


		<div class="form-container">
			<h3 class="form-title text-center">Update Attendance</h3>

			<form method="GET">
				<label for="standard">Select Standard:</label> <select
					name="standard" id="standard" class="form-select" required>
					<option value="" disabled selected>Select Standard</option>
					<option value="8"
						<%="8".equals(selectedStandard) ? "selected" : ""%>>8th</option>
					<option value="9"
						<%="9".equals(selectedStandard) ? "selected" : ""%>>9th</option>
					<option value="10"
						<%="10".equals(selectedStandard) ? "selected" : ""%>>10th</option>
				</select> <label for="date">Select Date:</label> <input type="date"
					name="date" id="date" class="form-control"
					value="<%=request.getParameter("date")%>"
					max="<%=java.time.LocalDate.now()%>" required>

				<button type="submit" class="btn btn-primary mt-2">Filter</button>
			</form>


			<%
			if (!attendanceList.isEmpty()) {
			%>
			<form action="UpdateAttendanceServlet" method="POST">
				<input type="hidden" name="standard" value="<%=selectedStandard%>">
				<input type="hidden" name="date" value="<%=selectedDate%>">

				<table class="table table-bordered mt-3">
					<thead>
						<tr>
							<th>Student ID</th>
							<th>Student Name</th>
							<th>Attendance Status</th>
							<th>Update Status</th>
						</tr>
					</thead>
					<tbody>
						<%
						for (Attendance att : attendanceList) {
						%>
						<tr>
							<td><%=att.getStudentId()%></td>
							<td><%=att.getStudentName()%></td>
							<td><%=att.getStatus()%></td>
							<td><select name="attendance_<%=att.getAttendanceId()%>"
								class="form-select">
									<option value="Present"
										<%="Present".equals(att.getStatus()) ? "selected" : ""%>>Present</option>
									<option value="Absent"
										<%="Absent".equals(att.getStatus()) ? "selected" : ""%>>Absent</option>
							</select></td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>

				<div class="text-center">
					<button type="submit" class="btn btn-success">Update
						Attendance</button>
				</div>
			</form>
			<%
			} else if (selectedStandard != null && selectedDate != null) {
			%>
			<p class="text-center text-danger mt-3">No attendance records
				found for the selected standard and date.</p>
			<%
			}
			%>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
