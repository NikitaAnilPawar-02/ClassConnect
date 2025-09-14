package com.user.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.DB.DBConnect;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CancelPaymentServlet")
public class CancelPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer studentId = (Integer) session.getAttribute("studentId");

		if (studentId == null) {
			response.getWriter().write("Invalid student ID");
			return;
		}

		try (Connection conn = DBConnect.getConn()) {
			String updateQuery = "UPDATE payments SET paymentStatus = 'Canceled' WHERE studentId = ? AND paymentStatus = 'Pending'";
			PreparedStatement ps = conn.prepareStatement(updateQuery);
			ps.setInt(1, studentId);

			int rowsAffected = ps.executeUpdate();
			ps.close();

			if (rowsAffected > 0) {
				response.getWriter().write("Payment canceled successfully");
			} else {
				response.getWriter()
						.write("No pending payment found to cancel.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("Error canceling payment.");
		}
	}
}
