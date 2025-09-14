package com.admin.servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DB.DBConnect;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/ViewAttendanceServlet")
public class ViewAttendanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("userType") == null) {
			response.sendRedirect("../login.jsp");
			return;
		}

		String userType = (String) session.getAttribute("userType");
		if (!"Admin".equals(userType)) {
			response.sendRedirect("../login.jsp");
			return;
		}

		String date = request.getParameter("date");
		String studentId = request.getParameter("studentId");

		List<String> attendanceData = new ArrayList<>();

		try (Connection conn = DBConnect.getConn()) {
			String sql = "SELECT studentId, status, date FROM attendance WHERE studentId = ? AND date = ?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, studentId);
				stmt.setString(2, date);

				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					String record = rs.getString("studentId") + ","
							+ rs.getString("status") + ","
							+ rs.getString("date");
					attendanceData.add(record);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("attendanceData", attendanceData);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/admin/DashboardServlet");
		dispatcher.forward(request, response);
	}
}
