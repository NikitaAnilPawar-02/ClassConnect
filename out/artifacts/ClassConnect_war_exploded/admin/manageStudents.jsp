<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
%>
<%@page import="java.util.List"%>
<%@page import="com.entity.Student"%>
<%@page import="com.DAO.StudentDAOImpl"%>
<%@page import="com.DB.DBConnect"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: Manage Students</title>
<%@include file="/all_components/allCSS.css"%>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

	<!-- Sidebar -->
	<%@include file="/all_components/sidebar.jsp"%>

	<div class="content">
		<%@include file="/all_components/navbar.jsp"%>
		<%
    String successMsg = (String) session.getAttribute("successMsg");
    String errorMsg = (String) session.getAttribute("errorMsg");

    if (successMsg != null) {
%>
		<div class="alert alert-success alert-dismissible fade show"
			role="alert">
			<%= successMsg %>
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>
		<%
        session.removeAttribute("successMsg"); 
    } else if (errorMsg != null) {
%>
		<div class="alert alert-danger alert-dismissible fade show"
			role="alert">
			<%= errorMsg %>
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>
		<%
        session.removeAttribute("errorMsg"); 
    }
%>


		<div class="container">
			<h3 class="text-center">Manage Students</h3>

			<table class="table table-striped">
				<thead>
					<tr>
						<th>ID</th>
						<th>Full Name</th>
						<th>Email</th>
						<th>Phone</th>
						<th>Standard</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<%
                    StudentDAOImpl studentDAO = new StudentDAOImpl();
                    List<Student> studentList = studentDAO.getAllStudents();
                    for (Student s : studentList) {
                %>
					<tr>
						<td><%= s.getStudentId() %></td>
						<td><%= s.getStudentName() %></td>
						<td><%= s.getStudentEmail() %></td>
						<td><%= s.getStudentPhnNo() != null ? s.getStudentPhnNo() : "N/A" %></td>
						<td><%= s.getStudentStandard() %></td>
						<td><a href="editStudent.jsp?id=<%= s.getStudentId() %>"
							class="btn btn-warning btn-sm">Edit</a> <a href="#"
							class="btn btn-danger btn-sm"
							onclick="showConfirmModal('<%= request.getContextPath() %>/admin/DeleteStudentServlet?id=<%= s.getStudentId() %>')">Delete</a>
						</td>
					</tr>
					<% } %>
				</tbody>
			</table>
		</div>
	</div>

	<div class="modal fade" id="confirmModal" tabindex="-1"
		aria-labelledby="confirmModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="confirmModalLabel">Confirm
						Deletion</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">Are you sure you want to delete this
					student?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancel</button>
					<a href="#" id="confirmDeleteBtn" class="btn btn-danger">Delete</a>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	<script>
   
    function showConfirmModal(deleteUrl) {
      
        document.getElementById("confirmDeleteBtn").href = deleteUrl;
        var confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));
        confirmModal.show();
    }
</script>
</body>
</html>
