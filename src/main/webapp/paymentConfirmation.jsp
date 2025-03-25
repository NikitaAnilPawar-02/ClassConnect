<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    if (session == null || session.getAttribute("userType") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

 
    String studentName = (String) session.getAttribute("studentName");
    Integer studentStandard = (Integer) session.getAttribute("studentStandard");
    String paymentStatus = (String) session.getAttribute("paymentStatus");

    if (paymentStatus == null) {
        paymentStatus = "Pending"; 
    }

    String statusClass = "warning";
    if ("Success".equals(paymentStatus)) {
        statusClass = "success";
    } else if ("Failed".equals(paymentStatus)) {
        statusClass = "danger";
    }

    String successMessage = (String) session.getAttribute("successMessage");
    String errorMessage = (String) session.getAttribute("errorMessage");

    if (successMessage != null) {
        session.removeAttribute("successMessage");
    }
    if (errorMessage != null) {
        session.removeAttribute("errorMessage");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ClassConnect: Payment Confirmation</title>
    <%@include file="all_components/allCSS.css" %>
    <link rel="stylesheet" href="all_components/style.css">
</head>
<body>
    <%@include file="sidebar.jsp" %>
    <div class="content">
        <%@include file="navbar.jsp" %>

        <div class="container mt-5">
            <h2 class="text-center">Payment Confirmation</h2>

            <% if (successMessage != null) { %>
                <div class="alert alert-success text-center">
                    <%= successMessage %>
                </div>
            <% } else if (errorMessage != null) { %>
                <div class="alert alert-danger text-center">
                    <%= errorMessage %>
                </div>
            <% } %>

            <div class="table-responsive mt-4">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Student Name</th>
                            <th>Standard</th>
                            <th>Paid Fees</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><%= studentName %></td>
                            <td><%= studentStandard %></td>
                            <td>Rs.  <%= (studentStandard == 8) ? "8000" : (studentStandard == 9) ? "9000" : "10000" %></td>
                            <td><span class="badge text-bg-<%= statusClass %>"><%= paymentStatus %></span></td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="text-center mt-4">
                <a href="fees.jsp" class="btn btn-primary">Go Back to Fee Status</a>
            </div>
        </div>
    </div>
</body>
</html>
