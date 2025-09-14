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
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: Add Student</title>
<%@include file="/all_components/allCSS.css"%>
</head>
<body>

	<!-- Sidebar -->
	<%@include file="/all_components/sidebar.jsp"%>

	<!-- Main Content -->
	<div class="content">
		<!-- Navbar -->
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
			<h3 class="form-title text-center">Add New Student</h3>
			<form id="addStudentForm" action="AddStudentServlet" method="POST"
				onsubmit="return validateForm()">
				<div class="row">
					<div class="col-md-6">
						<div class="mb-3">
							<label for="studentName" class="form-label">Full Name</label> <input
								type="text" class="form-control" id="studentName"
								name="studentName" placeholder="Enter student's full name"
								required>
							<div id="studentNameError" class="invalid-feedback"
								style="display: none;">Please enter a valid Name.</div>
						</div>

						<div class="mb-3">
							<label for="studentEmail" class="form-label">Email
								Address</label> <input type="email" class="form-control"
								id="studentEmail" name="studentEmail"
								placeholder="Enter student's email" required>
						</div>

						<div class="mb-3">
							<label for="studentPhnNo" class="form-label">Student
								Phone Number</label> <input type="tel" class="form-control"
								id="studentPhnNo" name="studentPhnNo"
								placeholder="Enter student's phone number (Optional)">
							<div id="studentPhoneError" class="invalid-feedback"
								style="display: none;">Please enter a valid 10-digit
								Indian phone number.</div>
						</div>

						<div class="mb-3">
							<label class="form-label">Gender</label>
							<div class="d-flex">
								<div class="form-check me-3">
									<input class="form-check-input" type="radio" id="genderMale"
										name="studentGender" value="Male" required> <label
										class="form-check-label" for="genderMale">Male</label>
								</div>
								<div class="form-check me-3">
									<input class="form-check-input" type="radio" id="genderFemale"
										name="studentGender" value="Female" required> <label
										class="form-check-label" for="genderFemale">Female</label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="radio" id="genderOther"
										name="studentGender" value="Other" required> <label
										class="form-check-label" for="genderOther">Other</label>
								</div>
							</div>
						</div>

						<div class="mb-3">
							<label for="studentDOB" class="form-label">Date of Birth</label>
							<input type="date" class="form-control" id="studentDOB"
								name="studentDOB" min="2000-01-01" max="2014-12-31" required>
						</div>


						<div class="mb-3">
							<label for="studentAddress" class="form-label">Address</label>
							<textarea class="form-control" id="studentAddress"
								name="studentAddress" rows="3"
								placeholder="Enter student's address" required></textarea>
						</div>
					</div>


					<div class="col-md-6">

						<div class="mb-3">
							<label for="parentName" class="form-label">Parent's Name</label>
							<input type="text" class="form-control" id="parentName"
								name="parentName" placeholder="Enter parent's full name"
								required>
							<div id="parentNameError" class="invalid-feedback"
								style="display: none;">Please enter a valid Name.</div>
						</div>


						<div class="mb-3">
							<label for="parentEmail" class="form-label">Parent's
								Email Address</label> <input type="email" class="form-control"
								id="parentEmail" name="parentEmail"
								placeholder="Enter parent's email" required>
						</div>


						<div class="mb-3">
							<label for="parentPhnNo" class="form-label">Parent Phone
								Number</label> <input type="tel" class="form-control" id="parentPhnNo"
								name="parentPhnNo" placeholder="Enter parent's phone number"
								required>
							<div id="parentPhoneError" class="invalid-feedback"
								style="display: none;">Please enter a valid 10-digit phone number.</div>
						</div>


						<div class="mb-3">
							<label for="relationToStudent" class="form-label">Relation
								to Student</label> <select class="form-select" id="relationToStudent"
								name="relationToStudent" required>
								<option value="" selected disabled>Select Relation</option>
								<option value="Father">Father</option>
								<option value="Mother">Mother</option>
								<option value="Guardian">Guardian</option>
							</select>
						</div>


						<div class="mb-3">
							<label for="studentStandard" class="form-label">Standard</label>
							<select class="form-select" id="studentStandard"
								name="studentStandard" required onchange="updateFees()">
								<option value="" selected disabled>Select Standard</option>
								<option value="8">8th</option>
								<option value="9">9th</option>
								<option value="10">10th</option>
							</select>
						</div>


						<div class="mb-3">
							<label for="studentFee" class="form-label">Fees (Rs.)</label> <input
								type="number" class="form-control" id="studentFee"
								name="studentFee" placeholder="Auto-Calculated" readonly>
						</div>
					</div>
				</div>

				<div class="text-center">
					<button type="submit" class="btn btn-primary w-100">Add
						Student</button>
				</div>
			</form>
		</div>
	</div>

	<script>
		const feesStructure = {
			8 : 8000,
			9 : 9000,
			10 : 10000
		};

		function updateFees() {
			const standard = document.getElementById("studentStandard").value;
			const fees = feesStructure[standard] || "N/A";
			document.getElementById("studentFee").value = `${fees}`;
		}

		function validatePhoneNumber(phoneNumber) {
			const phonePattern = /^[6-9][0-9]{9}$/;
			return phonePattern.test(phoneNumber);
		}

		function validateName(name) {
			const namePattern = /^[a-zA-Z\s]+$/;
			return namePattern.test(name);
		}

		function validateForm() {
			const studentPhone = document.getElementById("studentPhnNo").value;
			const parentPhone = document.getElementById("parentPhnNo").value;
			const studentName = document.getElementById("studentName").value;
			const parentName = document.getElementById("parentName").value;

			if (studentPhone && !validatePhoneNumber(studentPhone)) {
				document.getElementById("studentPhoneError").style.display = "block";
				return false;
			} else {
				document.getElementById("studentPhoneError").style.display = "none";
			}

			if (!validatePhoneNumber(parentPhone)) {
				document.getElementById("parentPhoneError").style.display = "block";
				return false;
			} else {
				document.getElementById("parentPhoneError").style.display = "none";
			}

			if (!validateName(studentName)) {
				document.getElementById("studentNameError").style.display = "block";
				return false;
			} else {
				document.getElementById("studentNameError").style.display = "none";
			}

			if (parentName && !validateName(parentName)) {
				document.getElementById("parentNameError").style.display = "block";
				return false;
			} else {
				document.getElementById("parentNameError").style.display = "none";
			}
			return true;
		}
	</script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>