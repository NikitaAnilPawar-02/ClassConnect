package com.user.servlet;
import java.io.IOException;
import java.util.List;

import com.DAO.AttendanceDAOImpl;
import com.entity.Attendance;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/StudentAttendanceServlet")
public class StudentAttendanceServlet extends HttpServlet {
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

		Integer studentId = (Integer) session.getAttribute("studentId");
		System.out.println("Student ID from session: " + studentId);

		if (studentId == null) {
			System.out.println(
					"Redirecting to login.jsp because studentId is null.");
			response.sendRedirect("login.jsp");
			return;
		}

		try {
			AttendanceDAOImpl attendanceDAO = new AttendanceDAOImpl();
			List<Attendance> attendanceList = attendanceDAO
					.getStudentAttendance(studentId);

			request.setAttribute("attendanceList", attendanceList);
			request.getRequestDispatcher("StudentAttendance.jsp")
					.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
