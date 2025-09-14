package com.admin.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.DAO.AttendanceDAOImpl;
import com.entity.Attendance;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/UpdateAttendanceServlet")
public class UpdateAttendanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("userType") == null
				|| !"Admin".equals(session.getAttribute("userType"))) {
			response.sendRedirect("../login.jsp");
			return;
		}

		try {
			int standard = Integer.parseInt(request.getParameter("standard"));
			String dateStr = request.getParameter("date");

			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(dateStr, formatter);

			AttendanceDAOImpl attendanceDAO = new AttendanceDAOImpl();
			List<Attendance> attendanceList = attendanceDAO
					.getAttendanceByStandardAndDate(standard, dateStr);

			if (attendanceList.isEmpty()) {
				session.setAttribute("infoMsg",
						"No attendance records found for the selected standard and date.");
			} else {
				boolean updated = false;
				for (Attendance att : attendanceList) {
					String updatedStatus = request.getParameter(
							"attendance_" + att.getAttendanceId());

					if (updatedStatus != null
							&& !updatedStatus.equals(att.getStatus())) {
						attendanceDAO.updateAttendance(att.getAttendanceId(),
								updatedStatus);
						updated = true;
					}
				}

				if (updated) {
					session.setAttribute("successMsg",
							"Attendance updated successfully.");
				} else {
					session.setAttribute("infoMsg",
							"No changes were made. Attendance was already up to date.");
				}
			}

		} catch (NumberFormatException e) {
			session.setAttribute("errorMsg",
					"Invalid input format. Please select a valid standard.");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg",
					"Something went wrong while updating attendance.");
		}

		response.sendRedirect("updateAttendance.jsp?standard="
				+ request.getParameter("standard") + "&date="
				+ request.getParameter("date"));
	}
}
