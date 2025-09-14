package com.admin.servlet;

import java.io.IOException;
import java.sql.*;
import com.DB.DBConnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/DeleteNoticeServlet")
public class DeleteNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		String id = request.getParameter("id");
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBConnect.getConn();
			String sql = "DELETE FROM notices WHERE noticeId=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				session.setAttribute("successMsg",
						"Notice deleted successfully.");
			} else {
				session.setAttribute("errorMsg", "Failed to delete notice.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Error deleting notice.");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		response.sendRedirect("notices.jsp");
	}
}
