package com.admin.servlet;

import java.io.IOException;

import com.DAO.AttendanceDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/DeleteAttendanceServlet")
public class DeleteAttendanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession();

		if (session == null || session.getAttribute("userType") == null
				|| !"Admin".equals(session.getAttribute("userType"))) {
			res.sendRedirect("../login.jsp");
			return;
		}

		try {
			int studentId = Integer.parseInt(req.getParameter("studentId"));
			String date = req.getParameter("date");

			AttendanceDAOImpl attendanceDAO = new AttendanceDAOImpl();
			boolean success = attendanceDAO.deleteAttendance(studentId, date);

			if (success) {
				session.setAttribute("message",
						"Attendance record deleted successfully.");
			} else {
				session.setAttribute("message",
						"Failed to delete attendance record.");
			}

			res.sendRedirect("viewAttendance.jsp?standard="
					+ req.getParameter("standard") + "&date=" + date);
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message",
					"An error occurred while deleting the attendance record.");
			res.sendRedirect("viewAttendance.jsp");
		}
	}
}
