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
<%@ page import="com.entity.Student, com.entity.Parent"%>
<%
    Student student = (Student) request.getAttribute("student");
    Parent parent = (Parent) request.getAttribute("parent");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: Profile</title>
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
			<h2 class="text-center">Profile Details</h2>
			<hr>

			<div class="row">
				<div class="col-md-6">
					<div class="card shadow">
						<div class="card-header bg-primary text-white">Student
							Details</div>
						<div class="card-body">
							<p>
								<strong>Name:</strong>
								<%= student.getStudentName() %></p>
							<p>
								<strong>Email:</strong>
								<%= student.getStudentEmail() %></p>
							<p>
								<strong>Phone:</strong>
								<%= student.getStudentPhnNo() != null ? student.getStudentPhnNo() : "N/A" %></p>
							<p>
								<strong>Gender:</strong>
								<%= student.getStudentGender() %></p>
							<p>
								<strong>Date of Birth:</strong>
								<%= student.getStudentDOB() %></p>
							<p>
								<strong>Address:</strong>
								<%= student.getStudentAddress() %></p>
							<p>
								<strong>Standard:</strong>
								<%= student.getStudentStandard() %></p>
							<p>
								<strong>Fees:</strong> Rs.
								<%= student.getStudentFee() %></p>
						</div>
					</div>
				</div>

				<div class="col-md-6">
					<div class="card shadow">
						<div class="card-header bg-success text-white">Parent
							Details</div>
						<div class="card-body">
							<% if (parent != null) { %>
							<p>
								<strong>Name:</strong>
								<%= parent.getParentName() %></p>
							<p>
								<strong>Email:</strong>
								<%= parent.getParentEmail() %></p>
							<p>
								<strong>Phone:</strong>
								<%= parent.getParentPhnNo() %></p>
							<p>
								<strong>Relation:</strong>
								<%= parent.getRelationToStudent() %></p>
							<% } else { %>
							<p class="text-danger">Parent details not available.</p>
							<% } %>
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