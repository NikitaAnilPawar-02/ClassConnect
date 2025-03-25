<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.DB.DBConnect"%>
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

Integer studentId = (Integer) session.getAttribute("studentId");
Integer studentStandard = (Integer) session.getAttribute("studentStandard");
double studentFee = 0;

if (studentStandard == 8)
	studentFee = 8000;
else if (studentStandard == 9)
	studentFee = 9000;
else if (studentStandard == 10)
	studentFee = 10000;

String studentName = null;
Double amountDue = 0.0;
Double amountPaid = 0.0;
String paymentStatus = "Pending";

if (studentId != null) {
	try (Connection conn = DBConnect.getConn();
	PreparedStatement stmt = conn.prepareStatement(
			"SELECT s.studentName, p.amountDue, p.amountPaid, p.paymentStatus FROM student s JOIN payments p ON s.studentId = p.studentId WHERE s.studentId=?")) {

		stmt.setInt(1, studentId);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
	studentName = rs.getString("studentName");
	amountDue = rs.getDouble("amountDue");
	amountPaid = rs.getDouble("amountPaid");
	paymentStatus = rs.getString("paymentStatus");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

String statusClass = "warning";
if ("Success".equals(paymentStatus))
	statusClass = "success";
else if ("Failed".equals(paymentStatus))
	statusClass = "danger";
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
	<%@include file="sidebar.jsp"%>
	<div class="content">
		<%@include file="navbar.jsp"%>
		<h2 class="text-center">Fee Payment</h2>

		<%
	
		if (request.getParameter("success") != null) {
			session.removeAttribute("errorMessage"); 
			session.setAttribute("successMessage", request.getParameter("success"));
		}
		if (request.getParameter("error") != null) {
			session.removeAttribute("successMessage"); 
			session.setAttribute("errorMessage", request.getParameter("error"));
		}

		String successMessage = (String) session.getAttribute("successMessage");
		String errorMessage = (String) session.getAttribute("errorMessage");

		if (successMessage != null) {
		%>
		<div class="alert alert-success alert-dismissible fade show"
			role="alert">
			<%=successMessage%>
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>
		<%
		session.removeAttribute("successMessage"); 
		}
		if (errorMessage != null) {
		%>
		<div class="alert alert-danger alert-dismissible fade show"
			role="alert">
			<%=errorMessage%>
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>
		<%
		session.removeAttribute("errorMessage"); 
		}
		%>

		<div class="container mt-5">
			<h4>
				Fee Status for
				<%=(studentName != null) ? studentName : "Unknown"%></h4>
			<p>Below is the fee status of the student. Please make the
				payment if any fees are due.</p>

			<div class="table-responsive mt-4">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Student Name</th>
							<th>Due Fees</th>
							<th>Paid Fees</th>
							<th>Status</th>
							<th>Enter Amount</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><%=(studentName != null) ? studentName : "N/A"%></td>
							<td>Rs. <%=amountDue%></td>
							<td>Rs. <%=amountPaid%></td>
							<td><span class="badge text-bg-<%=statusClass%>"><%=paymentStatus%></span></td>

							<%
							if (amountDue > 0) {
							%>
							<td><input type="number" id="paymentAmount"
								class="form-control" placeholder="Enter amount" min="1"
								max="<%=amountDue%>" required>
								<div class="invalid-feedback">
									Please enter a valid amount between 1 and
									<%=amountDue%></div></td>
							<td>
								<button id="pay-btn" class="btn btn-primary">Pay Now</button>
							</td>
							<%
							} else {
							%>
							<td colspan="2" class="text-center text-success"><strong>No
									Due</strong></td>
							<%
							}
							%>
							<%
							if ("Success".equals(paymentStatus)) {
							%>
							<td colspan="2"><a
								href="DownloadReceiptServlet?studentId=<%=studentId%>"
								class="btn btn-success">Download Receipt</a></td>
							<%
							}
							%>

						</tr>

					</tbody>
				</table>

			</div>
		</div>
	</div>

	<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
	<script>
	document.addEventListener("DOMContentLoaded", function () {
	    let payBtn = document.getElementById("pay-btn");

	    if (payBtn) {
	        payBtn.addEventListener("click", function () {
	            let paymentAmount = document.getElementById("paymentAmount").value;

	            if (!paymentAmount || isNaN(paymentAmount) || paymentAmount <= 0 || paymentAmount > <%=amountDue%>) {
	                document.getElementById("paymentAmount").classList.add("is-invalid");
	                return;
	            } else {
	                document.getElementById("paymentAmount").classList.remove("is-invalid");
	            }

	            fetch("CreateOrderServlet", {
	                method: "POST",
	                headers: { "Content-Type": "application/x-www-form-urlencoded" },
	                body: `studentId=<%=studentId%>&amount=${paymentAmount}`
	            })
	            .then(response => response.json())
	            .then(order => {
	                if (order.error) {
	                    window.location.href = "fees.jsp?error=Error creating payment order.";
	                    return;
	                }

	                var options = {
	                    key: "rzp_test_bGve6FnJr1tdg7",
	                    amount: order.amount,
	                    currency: "INR",
	                    name: "Gurukrupa Classes",
	                    description: "Fee Payment",
	                    order_id: order.id,
	                    handler: function (response) {
	                        fetch("PaymentSuccessServlet", {
	                            method: "POST",
	                            headers: { "Content-Type": "application/x-www-form-urlencoded" },
	                            body: `studentId=<%=studentId%>&amountPaid=${paymentAmount}&razorpayPaymentId=${response.razorpay_payment_id}`
	                        })
	                        .then(response => response.text())
	                        .then(result => {
	                            window.location.href = "fees.jsp?success=Payment successful!";
	                        })
	                        .catch(error => {
	                            window.location.href = "fees.jsp?error=Payment verification failed.";
	                        });
	                    },
	                    modal: {
	                        escape: true,
	                        ondismiss: function () {
	                            fetch("CancelPaymentServlet", {
	                                method: "POST",
	                                headers: { "Content-Type": "application/x-www-form-urlencoded" },
	                                body: "studentId=" + <%=studentId%>
	                            }).then(() => {
	                                window.location.href = "fees.jsp?error=Payment was canceled.";
	                            }).catch(() => {
	                                window.location.href = "fees.jsp?error=Failed to handle cancellation.";
	                            });
	                        }
	                    }

	                };

	                var rzp = new Razorpay(options);
	                rzp.open();
	            })
	            .catch(error => {
	                window.location.href = "fees.jsp?error=An error occurred while processing payment.";
	            });
	        });
	    }
	});

	</script>

</body>
</html>
