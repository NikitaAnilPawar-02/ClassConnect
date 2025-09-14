package com.admin.servlet;
import com.DAO.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/DeleteStudentServlet")
public class DeleteStudentServlet extends HttpServlet {
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

		try {
			int studentId = Integer.parseInt(request.getParameter("id"));
			System.out.println(
					"Received request to delete student with ID: " + studentId);

			StudentDAO studentDAO = new StudentDAOImpl();
			boolean deleted = studentDAO.deleteStudent(studentId);

			if (deleted) {
				request.getSession().setAttribute("successMsg",
						"Student Deleted Successfully.");
			} else {
				request.getSession().setAttribute("errorMsg",
						"Deletion Failed.");
			}
			response.sendRedirect("manageStudents.jsp");

		} catch (Exception e) {
			System.err.println(
					"Error in DeleteStudentServlet: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
