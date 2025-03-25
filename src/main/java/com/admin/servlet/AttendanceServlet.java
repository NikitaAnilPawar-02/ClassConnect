package com.admin.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Enumeration;
import com.DB.DBConnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		String date = request.getParameter("attendanceDate");
		String selectedStandard = request.getParameter("attendanceStandard");

		if (date == null || date.trim().isEmpty()) {
			session.setAttribute("errorMsg", "Please select a valid date.");
			response.sendRedirect("attendance.jsp");
			return;
		}

		if (selectedStandard == null) {
			session.setAttribute("errorMsg", "Please select a class.");
			response.sendRedirect("attendance.jsp");
			return;
		}

		Connection conn = null;
		PreparedStatement ps = null;
		boolean attendanceMarked = false;

		try {
			conn = DBConnect.getConn();
			String sql = "INSERT INTO attendance (studentId, date, status) VALUES (?, ?, ?)";
			ps = conn.prepareStatement(sql);

			Enumeration<String> paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = paramNames.nextElement();
				if (paramName.startsWith("attendance_")) {
					int studentId = Integer.parseInt(paramName.split("_")[1]);
					String status = request.getParameter(paramName);

					ps.setInt(1, studentId);
					ps.setString(2, date);
					ps.setString(3, status);
					ps.addBatch();
					attendanceMarked = true;
				}
			}

			if (attendanceMarked) {
				ps.executeBatch();
				session.setAttribute("successMsg",
						"Attendance marked successfully.");
			} else {
				session.setAttribute("warningMsg",
						"No attendance data submitted.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg",
					"Failed to mark attendance. Please try again.");
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		response.sendRedirect("attendance.jsp");
	}
}
