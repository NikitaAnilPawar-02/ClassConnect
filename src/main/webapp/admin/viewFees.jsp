<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="java.util.List, com.entity.Payment" %>

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
List<Payment> feeRecords = (List<Payment>) request.getAttribute("feeRecords");
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
        <div class="container mt-4">
            <h2 class="text-center">Student Fee Records</h2>

            <table class="table table-bordered table-hover mt-3">
                <thead class="table-dark">
                    <tr>
                        <th>Student ID</th>
                        <th>Student Name</th>
                        <th>Standard</th>
                        <th>Parent Name</th>
                        <th>Amount Paid (₹)</th>
                        <th>Amount Due (₹)</th>
                        <th>Payment Date</th>
                        <th>Payment Status</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    if (feeRecords != null && !feeRecords.isEmpty()) {
                        for (Payment record : feeRecords) {
                    %>
                    <tr>
                        <td><%=record.getStudentId()%></td>
                        <td><%=record.getStudentName()%></td>
                        <td><%=record.getStudentStandard()%></td>
                        <td><%=record.getParentName()%></td>
                        <td>₹<%=record.getAmountPaid()%></td>
                        <td>₹<%=record.getAmountDue()%></td>
                        <td><%=record.getFormattedPaymentDate()%></td>
                        <td>
                            <% if ("Success".equals(record.getPaymentStatus())) { %>
                                <span class="badge bg-success">Success</span>
                            <% } else { %>
                                <span class="badge bg-danger">Pending</span>
                            <% } %>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="8" class="text-center">No fee records found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
