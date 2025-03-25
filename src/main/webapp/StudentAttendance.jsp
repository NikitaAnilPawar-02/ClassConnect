<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%
if (session == null || session.getAttribute("userType") == null) {
	response.sendRedirect("login.jsp");
	return;
}

String userType = (String) session.getAttribute("userType");

if (!"student".equals(userType) && !"parent".equals(userType)) {
	response.sendRedirect("login.jsp");
	return;
}
%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.entity.Attendance"%>
<%
List<Attendance> attendanceList = (List<Attendance>) request
		.getAttribute("attendanceList");
SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: Attendance</title>
<%@include file="all_components/allCSS.css"%>
<link rel="stylesheet" href="all_components/style.css">
</head>
<body>

	<%@include file="sidebar.jsp"%>

	<div class="content">
		<%@include file="navbar.jsp"%>

		<div class="container mt-4">
			<h2 class="text-center">Your Attendance Records</h2>
			<hr>

			<div class="row">
				<div class="col-md-12">
					<div class="card shadow">
						<div class="card-header bg-primary text-white">
							<h5 class="mb-0">Attendance History</h5>
						</div>
						<div class="card-body">
							<div class="card-body">
								<%
								if (attendanceList == null || attendanceList.isEmpty()) {
									out.println(
									"<p class='text-center text-danger'>No attendance records found.</p>");
								} else {
								%>

								<table class="table table-bordered text-center">
									<thead class="table-dark">
										<tr>
											<th>Date</th>
											<th>Status</th>
										</tr>
									</thead>
									<tbody>
										<%
										for (Attendance record : attendanceList) {
										%>
										<tr>
											<td><%=dateFormat.format(java.sql.Date.valueOf(record.getDate()))%></td>
											<td>
												<%
												if ("Present".equalsIgnoreCase(record.getStatus())) {
												%> <span class="badge bg-success text-capitalize">Present</span>
												<%
												} else {
												%> <span class="badge bg-danger text-capitalize">Absent</span>
												<%
												}
												%>
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
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
