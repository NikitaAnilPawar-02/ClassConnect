package com.admin.servlet;

import java.io.IOException;
import java.util.List;

import com.DAO.StudentDAO;
import com.DAO.StudentDAOImpl;
import com.entity.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/viewStudent")
public class ViewStudentServlet extends HttpServlet {

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
		System.out.println("ViewStudentServlet called!");
		StudentDAO studentDAO = new StudentDAOImpl();

		List<Student> students = studentDAO.getAllStudents();
		System.out.println("Number of students in servlet: " + students.size());

		for (Student student : students) {
			student.getParent();
		}

		request.setAttribute("students", students);

		request.getRequestDispatcher("/admin/viewStudent.jsp").forward(request,
				response);
	}
}