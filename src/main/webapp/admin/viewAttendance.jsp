<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, com.DAO.AttendanceDAOImpl, com.entity.Attendance"%>

<%
if (session == null || session.getAttribute("userType") == null
		|| !"Admin".equals(session.getAttribute("userType"))) {
	response.sendRedirect("../login.jsp");
	return;
}

String selectedStandard = request.getParameter("standard");
String selectedDate = request.getParameter("date");

List<Attendance> attendanceList = new ArrayList<>();
String errorMessage = null;

if (request.getParameter("standard") != null
		|| request.getParameter("date") != null) {
	if (selectedStandard != null && !selectedStandard.isEmpty()
	&& selectedDate != null && !selectedDate.isEmpty()) {
		try {
	int standard = Integer.parseInt(selectedStandard);
	AttendanceDAOImpl attendanceDAO = new AttendanceDAOImpl();
	attendanceList = attendanceDAO
			.getAttendanceByStandardAndDate(standard, selectedDate);

	if (attendanceList.isEmpty()) {
		errorMessage = "No attendance records found for the selected standard and date.";
	}
		} catch (NumberFormatException e) {
	errorMessage = "Invalid standard selection. Please select a valid class.";
		} catch (Exception e) {
	errorMessage = "An error occurred while fetching attendance. Please try again.";
		}
	} else {
		errorMessage = "Please select a standard and date.";
	}
}
%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: View Attendance</title>
<%@include file="/all_components/allCSS.css"%>
</head>
<body>

	<%@include file="/all_components/sidebar.jsp"%>

	<div class="content">
		<%@include file="/all_components/navbar.jsp"%>

		<div class="form-container">
			<h3 class="form-title text-center">View Attendance</h3>

			<%
			if (errorMessage != null) {
			%>
			<div class="alert alert-danger"><%=errorMessage%></div>
			<%
			}
			%>

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
					value="<%=selectedDate != null ? selectedDate : ""%>"
					max="<%=java.time.LocalDate.now()%>" required>

				<button type="submit" class="btn btn-primary mt-2">View
					Attendance</button>
			</form>

			<%
			if (!attendanceList.isEmpty()) {
			%>
			<table class="table table-bordered mt-3">
				<thead>
					<tr>
						<th>Student ID</th>
						<th>Student Name</th>
						<th>Attendance Status</th>
						<th>Action</th>
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
						<td>

							<form action="DeleteAttendanceServlet" method="POST">
								<input type="hidden" name="studentId"
									value="<%=att.getStudentId()%>"> <input type="hidden"
									name="date" value="<%=selectedDate%>"> <input
									type="hidden" name="standard" value="<%=selectedStandard%>">
								<button type="submit" class="btn btn-danger btn-sm">Delete</button>
							</form>
						</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
			<%
			}
			%>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
