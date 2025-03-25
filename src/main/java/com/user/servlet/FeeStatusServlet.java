package com.user.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.DB.DBConnect;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/FeeStatusServlet")
public class FeeStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userType = (String) session.getAttribute("userType");
		Integer studentId = (Integer) session.getAttribute("studentId");

		if (userType == null
				|| (!userType.equals("student") && !userType.equals("parent"))
				|| studentId == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		try (Connection conn = DBConnect.getConn()) {
			String sql = "SELECT s.studentName, s.StudentStandard, p.amountDue, p.amountPaid, p.paymentStatus "
					+ "FROM student s "
					+ "LEFT JOIN payments p ON s.studentId = p.studentId "
					+ "WHERE s.studentId = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, studentId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String studentName = rs.getString("studentName");
				int studentStandard = rs.getInt("studentStandard");
				double amountDue = rs.getDouble("amountDue");
				double amountPaid = rs.getDouble("amountPaid");
				String paymentStatus = rs.getString("paymentStatus");

				System.out.println(
						"FeeStatusServlet: Retrieved Data - StudentName: "
								+ studentName);
				System.out.println(
						"FeeStatusServlet: Standard: " + studentStandard);
				System.out
						.println("FeeStatusServlet: Amount Due: " + amountDue);
				System.out.println(
						"FeeStatusServlet: Amount Paid: " + amountPaid);
				System.out.println(
						"FeeStatusServlet: Payment Status: " + paymentStatus);

				request.setAttribute("studentName", studentName);
				request.setAttribute("studentStandard", studentStandard);
				request.setAttribute("amountDue", amountDue);
				request.setAttribute("amountPaid", amountPaid);
				request.setAttribute("paymentStatus", paymentStatus);
			} else {
				System.out.println(
						"FeeStatusServlet: No fee record found for student ID "
								+ studentId);
			}
		} catch (Exception e) {
			System.out
					.println("FeeStatusServlet: Error retrieving fee details.");
			e.printStackTrace();
		}

		System.out.println("FeeStatusServlet: Forwarding request to fees.jsp");
		request.getRequestDispatcher("fees.jsp").forward(request, response);
	}
}