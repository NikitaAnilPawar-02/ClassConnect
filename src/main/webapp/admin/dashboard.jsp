<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%
if (session == null || session.getAttribute("userType") == null) {
	response.sendRedirect("../login.jsp");
	return;
}

String userType = (String) session.getAttribute("userType");
if (!userType.equals("Admin")) {
	response.sendRedirect("../login.jsp");
	return;
}
%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: Admin Dashboard</title>
<%@include file="/all_components/allCSS.css"%>
</head>
<body>

	<!-- Sidebar -->
	<%@include file="/all_components/sidebar.jsp"%>

	<!-- Main Content -->
	<div class="content">
		<!-- Navbar -->
		<%@include file="/all_components/navbar.jsp"%>

		<div class="row text-center mb-4">
			<div class="col-md-3">
				<div class="card shadow">
					<div class="card-body">
						<h5 class="card-title">Total Students</h5>
						<p class="card-text">
							<%=request.getAttribute("totalStudents") != null
		? request.getAttribute("totalStudents")
		: 0%>
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="card shadow">
					<div class="card-body">
						<h5 class="card-title">Collected Fees</h5>
						<p class="card-text">
							Rs.
							<%=request.getAttribute("collectedFees") != null
		? request.getAttribute("collectedFees")
		: 0%>
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="card shadow">
					<div class="card-body">
						<h5 class="card-title">Pending Fees</h5>
						<p class="card-text">
							Rs.
							<%=request.getAttribute("pendingFees") != null
		? request.getAttribute("pendingFees")
		: 0%>
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="card shadow">
					<div class="card-body">
						<h5 class="card-title">Upcoming Notices</h5>
						<p class="card-text">
							<%=request.getAttribute("upcomingNotices") != null
		? request.getAttribute("upcomingNotices")
		: 0%>
							Notices
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row mt-4">
			<div class="col-md-12">
				<div class="card shadow">
					<div class="card-header">Student Attendance</div>
					<div class="card-body">
						<form action="ViewAttendanceServlet" method="GET">
							<p>Attendance by Date and ID</p>

							<input type="date" class="form-control mb-3" name="date" max="<%=java.time.LocalDate.now()%>" required>

							<input type="text" class="form-control mb-3"
								placeholder="Enter Student ID" name="studentId" required>

							<button type="submit" class="btn btn-warning w-100">View
								Attendance</button>
						</form>


						<%
						Object obj = request.getAttribute("attendanceData");
						if (obj instanceof List) {
							List<String> attendanceData = (List<String>) obj;
							if (attendanceData != null && !attendanceData.isEmpty()) {
						%>
						<h5 class="mt-3">Attendance Details:</h5>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Student ID</th>
									<th>Attendance Status</th>
									<th>Date</th>
								</tr>
							</thead>
							<tbody>
								<%
								for (String record : attendanceData) {
								%>
								<tr>
									<td><%=record.split(",")[0]%></td>
									<td><%=record.split(",")[1]%></td>
									<td><%=record.split(",")[2]%></td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
						<%
						} else {
						%>
						<p class="mt-3">No attendance data found for the selected date
							and student ID.</p>
						<%
						}
						}
						%>
					</div>
				</div>
			</div>
		</div>



		<div class="row mt-4">
			<div class="col-md-6">
				<div class="card shadow">
					<div class="card-header">Fees Payment</div>
					<div class="card-body">
						<p>View Payment Status</p>
						<input type="text" class="form-control mb-3"
							placeholder="Enter Student ID">
						<button class="btn btn-success w-100">View Payment</button>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="card shadow">
					<div class="card-header">Notices</div>
					<div class="card-body">
						<p>New Notices for Students</p>
						<ul>
							<%
							List<Map<String, String>> notices = (List<Map<String, String>>) request.getAttribute("notices");
							if (notices != null && !notices.isEmpty()) {
								for (Map<String, String> notice : notices) {
							%>
							<li><strong><%=notice.get("title")%></strong><br> <%=notice.get("description")%><br>
								<small>Posted on: <%=notice.get("date_posted")%></small></li>
							<%
							}
							} else {
							%>
							<li>No notices found.</li>
							<%
							}
							%>
						</ul>
						<a href="notices.jsp" class="btn btn-primary w-100">Add Notice</a>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>