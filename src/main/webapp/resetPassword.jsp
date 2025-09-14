<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: Reset Password</title>
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
			<form method="post" action="ResetPasswordServlet" class="form-login"
				onsubmit="return validatePassword();">
				<h1 class="h3 mb-5 text-center">
					<strong>RESET PASSWORD</strong>
				</h1>

				<div class="form-floating mb-2">
					<input type="password" class="form-control" id="newPassword"
						name="newPassword" placeholder="New Password" required> <label>New
						Password</label>
					<div id="passwordError" style="color: red; display: none;"></div>
				</div>

				<div class="form-floating mb-2">
					<input type="password" class="form-control" id="confirmPassword"
						name="confirmPassword" placeholder="Confirm Password" required>
					<label>Confirm Password</label>
					<div id="confirmPasswordError" style="color: red; display: none;"></div>
				</div>

				<div class="text-center mt-3">
					<button class="btn btn-info w-50" type="submit">
						<strong>Update Password</strong>
					</button>
				</div>
			</form>

			<script>
				function validatePassword() {
					let newPassword = document.getElementById("newPassword").value;
					let confirmPassword = document
							.getElementById("confirmPassword").value;
					let passwordError = document
							.getElementById("passwordError");
					let confirmPasswordError = document
							.getElementById("confirmPasswordError");

					let passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

					let isValid = true;

					if (!passwordPattern.test(newPassword)) {
						passwordError.innerText = "Password must be at least 8 characters long, include uppercase, lowercase, a number, and a special character.";
						passwordError.style.display = "block";
						isValid = false;
					} else {
						passwordError.style.display = "none";
					}

					if (newPassword !== confirmPassword) {
						confirmPasswordError.innerText = "Passwords do not match.";
						confirmPasswordError.style.display = "block";
						isValid = false;
					} else {
						confirmPasswordError.style.display = "none";
					}

					return isValid;
				}
			</script>
		</div>
	</div>
</body>
</html>