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
	background-color: rgba(255, 255, 255, 0.85);
	border-radius: 15px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.25);
	position: relative;
	z-index: 1;
}

.form-section, .image-section {
	flex: 1;
	padding: 20px;
}

.image-section {
	background: url('./images/form-side-image.jpeg') no-repeat center center;
	background-size: cover;
	border-top-left-radius: 15px;
	border-bottom-left-radius: 15px;
	min-height: 400px;
}

.form-login {
	width: 100%;
	max-width: 360px;
}

.form-login h1 {
	color: #333;
}

.form-floating label {
	color: #555;
}

@media ( max-width : 768px) {
	.container {
		flex-direction: column;
	}
	.image-section {
		border-radius: 15px 15px 0 0;
		height: 200px;
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
			<form action="ForgotPasswordServlet" method="post">
				<h1 class="h3 mb-5 text-center">
					<strong>FORGOT PASSWORD</strong>
				</h1>

				<%-- Display messages --%>
				<%
				String otpMessage = (String) session.getAttribute("otpMessage");
				if (otpMessage != null) {
				%>
				<div class="alert alert-danger text-center"><%=otpMessage%></div>
				<%
				session.removeAttribute("otpMessage");
				}
				%>

				<div class="form-floating mb-2">
					<input type="email" class="form-control" name="email"
						placeholder="Enter your Email" required> <label>Email
						address</label>
				</div>

				<div class="form-floating mb-2">
					<select class="form-select" name="userType" required>
						<option value="" disabled selected>Select User Type</option>
						<option value="student">Student</option>
						<option value="parent">Parent</option>
					</select> <label>User Type</label>
				</div>

				<div class="text-center mt-3">
					<button class="btn btn-info w-50 text-center" type="submit">
						<strong>Send OTP</strong>
					</button>
				</div>

				<div class="text-center mt-3">
					<a href="login.jsp" class="text-danger"
						style="text-decoration: none;">Back to Login</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
