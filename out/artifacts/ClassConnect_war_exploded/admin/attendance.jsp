<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*"%>
<%@ page import="com.DB.DBConnect"%>
<%@ page import="com.entity.Student"%>
<%@ page import="com.DAO.StudentDAOImpl"%>
<%@ page import="com.DAO.AttendanceDAOImpl"%>
<%@ page import="com.DAO.StudentDAO"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>

<%
if (session == null || session.getAttribute("userType") == null) {
	response.sendRedirect("../login.jsp");
	return;
}

String userType = (String) session.getAttribute("userType");
if (!"Admin".equals(userType)) {
	response.sendRedirect("../login.jsp");
	return;
}

String selectedStandard = request.getParameter("attendanceStandard");
String attendanceDate = request.getParameter("attendanceDate");
List<Student> students = new ArrayList<>();

if (selectedStandard != null) {
	students = new StudentDAOImpl()
	.getStudentsByClass(Integer.parseInt(selectedStandard));
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: Attendance</title>
<%@include file="/all_components/allCSS.css"%>
</head>
<body>

	<%@include file="/all_components/sidebar.jsp"%>
	<div class="content">
		<%@include file="/all_components/navbar.jsp"%>

		<%
		String successMsg = (String) session.getAttribute("successMsg");
		String warningMsg = (String) session.getAttribute("warningMsg");
		String errorMsg = (String) session.getAttribute("errorMsg");

		if (successMsg != null) {
		%>
		<div class="alert alert-success alert-dismissible fade show"
			role="alert">
			<%=successMsg%>
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>
		<%
		session.removeAttribute("successMsg");
		} else if (warningMsg != null) {
		%>
		<div class="alert alert-warning alert-dismissible fade show"
			role="alert">
			<%=warningMsg%>
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>
		<%
		session.removeAttribute("warningMsg");
		} else if (errorMsg != null) {
		%>
		<div class="alert alert-danger alert-dismissible fade show"
			role="alert">
			<%=errorMsg%>
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>
		<%
		session.removeAttribute("errorMsg");
		}
		%>

		<div class="form-container">
			<h3 class="form-title text-center">Mark Student Attendance</h3>

			<form method="GET">
				<div class="mb-3">
					<label for="attendanceDate" class="form-label">Select Date</label>
					<input type="date" class="form-control" id="attendanceDate"
						name="attendanceDate"
						value="<%=attendanceDate != null ? attendanceDate : ""%>"
						max="<%=java.time.LocalDate.now()%>" required>

				</div>

				<div class="mb-3">
					<label for="attendanceStandard" class="form-label">Select
						Standard</label> <select class="form-select" id="attendanceStandard"
						name="attendanceStandard" required onchange="this.form.submit()">
						<option value="" disabled selected>Select Standard</option>
						<option value="8"
							<%="8".equals(selectedStandard) ? "selected" : ""%>>8th</option>
						<option value="9"
							<%="9".equals(selectedStandard) ? "selected" : ""%>>9th</option>
						<option value="10"
							<%="10".equals(selectedStandard) ? "selected" : ""%>>10th</option>
					</select>
				</div>
			</form>

			<%
			if (!students.isEmpty()) {
			%>
			<form action="AttendanceServlet" method="POST">
				<input type="hidden" name="attendanceDate"
					value="<%=attendanceDate%>"> <input type="hidden"
					name="attendanceStandard" value="<%=selectedStandard%>">

				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Student ID</th>
							<th>Student Name</th>
							<th>Gender</th>
							<th>Standard</th>
							<th>Attendance</th>
						</tr>
					</thead>
					<tbody>
						<%
						for (Student student : students) {
						%>
						<tr>
							<td><%=student.getStudentId()%></td>
							<td><%=student.getStudentName()%></td>
							<td><%=student.getStudentGender()%></td>
							<td><%=student.getStudentStandard()%></td>
							<td><input type="radio"
								name="attendance_<%=student.getStudentId()%>" value="Present"
								required> Present <input type="radio"
								name="attendance_<%=student.getStudentId()%>" value="Absent"
								required> Absent</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>

				<div class="text-center">
					<button type="submit" class="btn btn-primary w-100">Submit
						Attendance</button>
				</div>
			</form>
			<%
			} else if (selectedStandard != null) {
			%>
			<p class="text-center text-danger">No students found for the
				selected standard.</p>
			<%
			}
			%>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
