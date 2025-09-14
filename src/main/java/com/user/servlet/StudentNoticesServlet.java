package com.user.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.DB.DBConnect;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/StudentNoticesServlet")
public class StudentNoticesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("userType") == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		String userType = (String) session.getAttribute("userType");
		if (!"student".equals(userType) && !"parent".equals(userType)) {
			response.sendRedirect("login.jsp");
			return;
		}

		List<Map<String, String>> notices = new ArrayList<>();

		try (Connection con = DBConnect.getConn();
				PreparedStatement stmt = con.prepareStatement(
						"SELECT noticeId, title, description, date_posted FROM notices ORDER BY date_posted DESC")) {

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Map<String, String> notice = new HashMap<>();
				notice.put("noticeId", String.valueOf(rs.getInt("noticeId")));
				notice.put("title", rs.getString("title"));
				notice.put("description", rs.getString("description"));
				notice.put("date_posted", rs.getString("date_posted"));

				notices.add(notice);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("notices", notices);
		request.getRequestDispatcher("StudentNotices.jsp").forward(request,
				response);
	}
}
