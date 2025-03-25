<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: Forgot Password</title>
<%@include file="/all_components/allCSS.css"%>
<style>
body {
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	margin: 0;
	background: url('./images/login-bg.jpg') no-repeat center center fixed;
	background-size: cover;
	overflow: hidden;
}

body::before {
	content: '';
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: rgba(0, 0, 0, 0.1);
	backdrop-filter: blur(8px);
	z-index: 0;
}

.container {
	display: flex;
	align-items: stretch;
	width: 70%;
	max-width: 1000px;
	background-color: rgba(255, 255, 255, 0.9);
	border-radius: 12px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
	position: relative;
	z-index: 1;
}

.form-section {
	flex: 1;
	padding: 20px 30px;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	min-height: 400px;
}

.image-section {
	flex: 1;
	background: url('./images/form-side-image.jpeg') no-repeat center center;
	background-size: cover;
	border-top-left-radius: 12px;
	border-bottom-left-radius: 12px;
	min-height: 350px;
}

.form-forgot {
	width: 100%;
	max-width: 360px;
}

.form-forgot h1 {
	color: #333;
	font-size: 22px;
	margin-bottom: 15px;
	text-align: center;
}

.form-floating label {
	color: #555;
	font-size: 14px;
}

.btn {
	padding: 8px 20px;
	font-size: 14px;
}

@media ( max-width : 768px) {
	.container {
		flex-direction: column;
		width: 90%;
	}
	.image-section {
		border-radius: 12px 12px 0 0;
		height: 180px;
		background-size: cover;
		background-position: center;
	}
}
</style>
</head>
<body>

	<div class="container">
		<div class="image-section"></div>

		<div class="form-section">
			<form action="ForgotPasswordServlet" method="post"
				class="form-forgot">
				<h1>
					<strong>Forgot Password</strong>
				</h1>

				<%
				HttpSession sessionMsg = request.getSession();
				String msg = (String) sessionMsg.getAttribute("msg");
				if (msg != null) {
				%>
				<div class="alert alert-danger text-center" role="alert">
					<%=msg%>
				</div>
				<%
				sessionMsg.removeAttribute("msg");
				}
				%>

				<div class="form-floating mb-2">
					<input type="email" class="form-control" id="email" name="email"
						placeholder="Enter your Email" required> <label
						for="email">Email address</label>
				</div>

				<div class="form-floating mb-2">
					<input type="password" class="form-control" id="newPassword"
						name="newPassword" placeholder="Enter New Password" required>
					<label for="newPassword">New Password</label>
				</div>

				<div class="form-floating mb-2">
					<input type="password" class="form-control" id="confirmPassword"
						name="confirmPassword" placeholder="Confirm New Password" required>
					<label for="confirmPassword">Confirm Password</label>
				</div>

				<div class="form-floating mb-2">
					<select class="form-select" id="userType" name="userType" required>
						<option value="" disabled selected>Select User Type</option>
						<option value="student">Student</option>
						<option value="parent">Parent</option>
					</select> <label for="userType">User Type</label>
				</div>

				<div class="text-center mt-3">
					<button class="btn btn-info w-50" type="submit">
						<strong>Reset Password</strong>
					</button>
				</div>

				<div class="text-center mt-3">
					<a href="login.jsp" class="text-danger"
						style="text-decoration: none;">Back to
						Login</a>
				</div>
			</form>
		</div>
	</div>

</body>
</html>
