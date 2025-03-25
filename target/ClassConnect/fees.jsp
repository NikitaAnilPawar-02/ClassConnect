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
<title>ClassConnect: Fee Payment</title>
<%@include file="all_components/allCSS.css"%>
<link rel="stylesheet" href="all_components/style.css">
</head>
<body>
	<!-- Sidebar -->
	<%@include file="sidebar.jsp"%>

	<div class="content">
		<!-- Navbar -->
		<%@include file="navbar.jsp"%>
		<h2 class="text-center">Fee Payment</h2>

		<div class="container mt-5">
			<h4>Fee Status for [Student Name]</h4>
			<p>Below is the fee status of a student. Please make the payment
				if any fees are due.</p>

			<div class="table-responsive mt-4">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Student Name</th>
							<th>Due Fees</th>
							<th>Paid Fees</th>
							<th>Status</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>

						<tr>
							<td>[Student Name]</td>
							<td>Rs.[Due Fees]</td>
							<td>Rs.[Paid Fees]</td>
							<td><span class="badge text-bg-[Status Class]">[Status]</span>
							</td>
							<td><a href="receipt.html?student_id=[Student ID]"
								class="btn btn-success">Download Receipt</a>
								<a href="payment.html?student_id=[Student ID]"
								class="btn btn-primary">Pay Now</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
