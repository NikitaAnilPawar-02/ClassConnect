package com.admin.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import com.DB.DBConnect;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/AddNoticeServlet")
public class AddNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
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

		String title = request.getParameter("noticeTitle");
		String description = request.getParameter("noticeDescription");
		String datePosted = LocalDate.now().toString();

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBConnect.getConn();
			String sql = "INSERT INTO notices (title, description, date_posted) VALUES (?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, title);
			stmt.setString(2, description);
			stmt.setString(3, datePosted);
			stmt.executeUpdate();

			session.setAttribute("successMsg", "Notice added successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed to add notice.");
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		response.sendRedirect("notices.jsp");
	}
}
