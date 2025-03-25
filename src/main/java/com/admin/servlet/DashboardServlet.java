package com.admin.servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.DAO.DashboardDAO;
import com.DAO.DashboardDAOImpl;
import com.DB.DBConnect;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/DashboardServlet")
public class DashboardServlet extends HttpServlet {
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

		DashboardDAO dashboardDAO = new DashboardDAOImpl();

		int totalStudents = dashboardDAO.getTotalStudents();
		double collectedFees = dashboardDAO.getCollectedFees();
		double pendingFees = dashboardDAO.getPendingFees();
		int upcomingNotices = dashboardDAO.getUpcomingNotices();

		List<Map<String, String>> notices = new ArrayList<>();
		try (Connection conn = DBConnect.getConn()) {
			String query = "SELECT * FROM notices ORDER BY date_posted DESC";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Map<String, String> notice = new HashMap<>();
				notice.put("title", rs.getString("title"));
				notice.put("description", rs.getString("description"));
				notice.put("date_posted", rs.getString("date_posted"));
				notices.add(notice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("totalStudents", totalStudents);
		request.setAttribute("collectedFees", collectedFees);
		request.setAttribute("pendingFees", pendingFees);
		request.setAttribute("upcomingNotices", upcomingNotices);
		request.setAttribute("notices", notices);

		System.out.println("Total Students: " + totalStudents);
		System.out.println("Collected Fees: Rs. " + collectedFees);
		System.out.println("Pending Fees: Rs. " + pendingFees);
		System.out.println("Upcoming Notices: " + upcomingNotices);

		request.getRequestDispatcher("/admin/dashboard.jsp").forward(request,
				response);
	}
}