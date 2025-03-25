package com.user.servlet;

import java.io.IOException;

import com.DAO.PaymentDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/PaymentSuccessServlet")
public class PaymentSuccessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer studentId = (Integer) session.getAttribute("studentId");
		Integer parentId = (session.getAttribute("parentId") != null)
				? (Integer) session.getAttribute("parentId")
				: null;
		String razorpayPaymentId = request.getParameter("razorpayPaymentId");
		double amountPaid = Double
				.parseDouble(request.getParameter("amountPaid"));

		System.out.println("Student ID: " + studentId);
		System.out.println("Parent ID: " + parentId);
		System.out.println("Razorpay Payment ID: " + razorpayPaymentId);
		System.out.println("Amount Paid: " + amountPaid);

		if (studentId == null || razorpayPaymentId == null || amountPaid <= 0) {
			session.setAttribute("errorMessage", "Invalid payment details.");
			session.setAttribute("paymentStatus", "Failed");
			System.out.println("Payment Failed: Invalid details.");
			response.sendRedirect("fees.jsp");
			return;
		}

		PaymentDAOImpl paymentDAO = new PaymentDAOImpl();
		boolean updated = paymentDAO.updatePaymentStatus(studentId, parentId,
				amountPaid, razorpayPaymentId);

		if (updated) {
			session.setAttribute("successMessage", "Payment successful!");
		} else {
			session.setAttribute("errorMessage", "Payment update failed.");
		}

		response.sendRedirect("fees.jsp");
	}
}