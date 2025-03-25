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
<title>ClassConnect: Fees Payment</title>
<%@include file="/all_components/allCSS.css"%>
</head>
<body>

	<!-- Sidebar -->
	<%@include file="/all_components/sidebar.jsp"%>

	<!-- Main Content -->
	<div class="content">
		<!-- Navbar -->
		<%@include file="/all_components/navbar.jsp"%>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
