<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="java.util.List, java.util.Map"%>
<%
if (session == null || session.getAttribute("userType") == null) {
	response.sendRedirect("login.jsp");
	return;
}

String userType = (String) session.getAttribute("userType");

if (!userType.equals("student") && !userType.equals("parent")) {
	response.sendRedirect("login.jsp");
	return;
}

List<Map<String, String>> notices = (List<Map<String, String>>) request
		.getAttribute("notices");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: Notices</title>
<%@include file="all_components/allCSS.css"%>
<link rel="stylesheet" href="all_components/style.css">
</head>
<body>

	<!-- Sidebar -->
	<%@include file="sidebar.jsp"%>

	<!-- Main Content -->
	<div class="content">
		<!-- Navbar -->
		<%@include file="navbar.jsp"%>
		<div class="container mt-4">
			<h2 class="text-center">Notices</h2>
			<hr>
			<div class="row">
				<div class="col-md-12">
					<div class="card shadow">
						<div class="card-header">Latest Notices</div>
						<div class="card-body">
							<%
							if (notices == null || notices.isEmpty()) {
							%>
							<p class="text-center">No notices available at the moment.</p>
							<%
							} else {
							%>
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>Title</th>
										<th>Description</th>
										<th>Date Posted</th>
									</tr>
								</thead>
								<tbody>
									<%
									for (Map<String, String> notice : notices) {
									%>
									<tr>
										<td><%=notice.get("title")%></td>
										<td><%=notice.get("description")%></td>
										<td><%=notice.get("date_posted")%></td>
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

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>