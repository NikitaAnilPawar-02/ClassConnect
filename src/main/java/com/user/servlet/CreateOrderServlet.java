package com.user.servlet;

import java.io.IOException;

import org.json.JSONObject;

import com.DAO.PaymentDAO;
import com.DAO.PaymentDAOImpl;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CreateOrderServlet")
public class CreateOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			int studentId = Integer.parseInt(request.getParameter("studentId"));

			Integer parentId = (session.getAttribute("parentId") != null)
					? (Integer) session.getAttribute("parentId")
					: null;

			double amount = Double.parseDouble(request.getParameter("amount"));
			int amountInPaise = (int) (amount * 100);

			RazorpayClient razorpay = new RazorpayClient(
					"rzp_test_bGve6FnJr1tdg7", "LqEVIxfRdFxlNBl1R9261brK");

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", amountInPaise);
			orderRequest.put("currency", "INR");
			orderRequest.put("payment_capture", 1);
			orderRequest.put("receipt", "txn_" + studentId);

			Order order = razorpay.orders.create(orderRequest);
			String razorpayPaymentId = order.get("id");

			PaymentDAO paymentDAO = new PaymentDAOImpl();
			boolean isPaymentCreated = paymentDAO.createPaymentRecord(studentId,
					parentId, amount, razorpayPaymentId);

			if (!isPaymentCreated) {
				session.setAttribute("errorMessage",
						"Failed to create payment record. Please try again.");
			} else {
				session.setAttribute("successMessage",
						"Payment initiated successfully.");
			}

			response.setContentType("application/json");
			response.getWriter().write(order.toString());

		} catch (Exception e) {
			session.setAttribute("errorMessage",
					"Error: Unable to process payment.");
			response.getWriter()
					.write("{\"error\":\"Failed to create order\"}");
			e.printStackTrace();
		}
	}
}