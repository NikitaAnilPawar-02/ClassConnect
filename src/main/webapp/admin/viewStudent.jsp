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
<%@ page import="java.util.List"%>
<%@ page import="com.entity.Student"%>
<%@ page import="com.entity.Parent"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: View Student</title>
<%@include file="/all_components/allCSS.css"%>
</head>
<body>

	<!-- Sidebar -->
	<%@include file="/all_components/sidebar.jsp"%>
	<!-- Main Content -->
	<div class="content">
		<!-- Navbar -->
		<%@include file="/all_components/navbar.jsp"%>
		<div class="table-container my-4">
			<h3 class="text-center mb-4">View Students</h3>
			<table
				class="table table-striped table-hover table-bordered shadow-sm">
				<thead class="table-dark text-center align-middle">
					<tr>
						<th>#</th>
						<th>Student Name</th>
						<th>Email</th>
						<th>Phone Number</th>
						<th>Gender</th>
						<th>Date of Birth</th>
						<th>Address</th>
						<th>Standard</th>
						<th>Fees (Rs.)</th>
						<th>Parent Details</th>
					</tr>
				</thead>
				<tbody>
					<%
					List<Student> students = (List<Student>) request.getAttribute("students");
					if (students != null) {
						for (Student student : students) {
							Parent parent = student.getParent();
					%>
					<tr>
						<td><%=student.getStudentId()%></td>
						<td><%=student.getStudentName()%></td>
						<td><%=student.getStudentEmail()%></td>
						<td><%=student.getStudentPhnNo()%></td>
						<td><%=student.getStudentGender()%></td>
						<td><%=student.getStudentDOB()%></td>
						<td><%=student.getStudentAddress()%></td>
						<td><%=student.getStudentStandard()%></td>
						<td><%=student.getStudentFee()%></td>
						<td>
							<button type="button" class="btn btn-info btn-sm"
								onclick="showParentDetails('<%=parent.getParentName()%>', 
                                                   '<%=parent.getParentEmail()%>', 
                                                   '<%=parent.getParentPhnNo()%>', 
                                                   '<%=parent.getRelationToStudent()%>')">
								View Parent Details</button>
						</td>
					</tr>
					<%
					}
					} else {
					%>
					<tr>
						<td colspan="10" class="text-center">No students found.</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</div>

	<div class="modal fade" id="parentDetailsModal" tabindex="-1"
		aria-labelledby="parentDetailsModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="parentDetailsModalLabel">Parent
						Details</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<ul class="list-group">
						<li class="list-group-item"><strong>Parent's Name:</strong> <span
							id="parentName"></span></li>
						<li class="list-group-item"><strong>Parent's Email:</strong>
							<span id="parentEmail"></span></li>
						<li class="list-group-item"><strong>Parent's Phone:</strong>
							<span id="parentPhone"></span></li>
						<li class="list-group-item"><strong>Relation to
								Student:</strong> <span id="parentRelation"></span></li>
					</ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	<script>
		function showParentDetails(parentName, parentEmail, parentPhone,
				parentRelation) {

			document.getElementById("parentName").innerText = parentName;
			document.getElementById("parentEmail").innerText = parentEmail;
			document.getElementById("parentPhone").innerText = parentPhone;
			document.getElementById("parentRelation").innerText = parentRelation;
			new bootstrap.Modal(document.getElementById('parentDetailsModal'))
					.show();
		}
	</script>

</body>
</html>