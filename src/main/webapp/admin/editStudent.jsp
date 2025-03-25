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
<%@page import="com.entity.Student"%>
<%@page import="com.entity.Parent"%>
<%@page import="com.DAO.StudentDAOImpl"%>
<%@page import="com.DAO.ParentDAOImpl"%>
<%@page import="com.DB.DBConnect"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: Edit Student</title>
<%@include file="/all_components/allCSS.css"%>
</head>
<body>

	<%@include file="/all_components/sidebar.jsp"%>

	<div class="content">
		<%@include file="/all_components/navbar.jsp"%>

		<div class="container">
			<h3 class="text-center">Edit Student & Parent Details</h3>

			<%
            int studentId = Integer.parseInt(request.getParameter("id"));
            StudentDAOImpl studentDAO = new StudentDAOImpl();
            Student student = studentDAO.getStudentById(studentId);
            
            ParentDAOImpl parentDAO = new ParentDAOImpl();
            Parent parent = parentDAO.getParentByStudentId(studentId);
        %>

			<form action="EditStudentServlet" method="POST">
				<input type="hidden" name="studentId"
					value="<%= student.getStudentId() %>">

				<div class="row">
					<div class="col-md-6">
						<h4>Student Details</h4>
						<div class="mb-3">
							<label class="form-label">Full Name</label> <input type="text"
								class="form-control" name="studentName"
								value="<%= student.getStudentName() %>" required>
						</div>
						<div class="mb-3">
							<label class="form-label">Email</label> <input type="email"
								class="form-control" name="studentEmail"
								value="<%= student.getStudentEmail() %>" required>
						</div>
						<div class="mb-3">
							<label class="form-label">Phone</label> <input type="text"
								class="form-control" name="studentPhnNo"
								value="<%= student.getStudentPhnNo() %>">
						</div>
						<div class="mb-3">
							<label class="form-label">Standard</label> <input type="number"
								class="form-control" name="studentStandard"
								value="<%= student.getStudentStandard() %>" required>
						</div>
					</div>

					<div class="col-md-6">
						<h4>Parent Details</h4>
						<div class="mb-3">
							<label class="form-label">Parent Name</label> <input type="text"
								class="form-control" name="parentName"
								value="<%= parent.getParentName() %>" required>
						</div>
						<div class="mb-3">
							<label class="form-label">Parent Email</label> <input
								type="email" class="form-control" name="parentEmail"
								value="<%= parent.getParentEmail() %>" required>
						</div>
						<div class="mb-3">
							<label class="form-label">Parent Phone</label> <input type="text"
								class="form-control" name="parentPhnNo"
								value="<%= parent.getParentPhnNo() %>" required>
						</div>
						<div class="mb-3">
							<label class="form-label">Relation to Student</label> <input
								type="text" class="form-control" name="relationToStudent"
								value="<%= parent.getRelationToStudent() %>" required>
						</div>
					</div>
				</div>

				<div class="text-center mt-3">
					<button type="submit" class="btn btn-primary">Update
						Student & Parent</button>
				</div>
			</form>
		</div>
	</div>

</body>
</html>
