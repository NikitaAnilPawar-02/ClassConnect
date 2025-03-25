<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
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
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ClassConnect: Dashboard</title>
<%@include file="all_components/allCSS.css"%>
<link rel="stylesheet" href="all_components/style.css">
</head>
<body>
	<!-- Sidebar -->
	<%@include file="sidebar.jsp"%>

	<div class="content">
		<!-- Navbar -->
		<%@include file="navbar.jsp"%>
		<h2 class="text-center mb-5">Welcome to Your Dashboard</h2>

		<div class="row">

			<div class="col-md-6 mb-4">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">Fee Payment</h5>
						<p>Pay your fees online</p>
						<a href="payment.html" class="btn btn-primary">Pay Now</a>
					</div>
				</div>
			</div>

			<div class="col-md-6 mb-4">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">Notices</h5>
						<p>Stay updated with the latest notices.</p>
						<a href="StudentNoticesServlet" class="btn btn-secondary">View
							Notices</a>
					</div>
				</div>
			</div>
		</div>

		<div class="row">

			<div class="col-md-6 mb-4">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">Attendance</h5>
						<p>Check your attendance record.</p>
						<a href="StudentAttendanceServlet" class="btn btn-info">View
							Attendance</a>
					</div>
				</div>
			</div>


			<div class="col-md-6 mb-4">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">Download Receipt</h5>
						<p>Download your payment receipt here.</p>
						<a href="receipt.html" class="btn btn-success">Download
							Receipt</a>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
