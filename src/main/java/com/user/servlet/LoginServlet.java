package com.user.servlet;

import java.io.IOException;

import com.DAO.ParentDAO;
import com.DAO.ParentDAOImpl;
import com.DAO.StudentDAO;
import com.DAO.StudentDAOImpl;
import com.entity.Parent;
import com.entity.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		try {
			String userEmail = req.getParameter("userEmail");
			String userPassword = req.getParameter("userPassword");

			if (userEmail.equals("admin@gmail.com") && userPassword.equals("admin")) {
				session.setAttribute("userType", "Admin");
				res.sendRedirect("admin/DashboardServlet");
				return;
			}

			StudentDAO studentDAO = new StudentDAOImpl();
			Student student = studentDAO.login(userEmail, userPassword);

			if (student != null) {
				session.setAttribute("userType", "student");
				session.setAttribute("studentId", student.getStudentId());
				session.setAttribute("userName", student.getStudentName());
				session.setAttribute("studentStandard", student.getStudentStandard());
				session.setAttribute("studentFee", student.getStudentFee());

				System.out.println("Student Logged In. Student ID: " + student.getStudentId());

				res.sendRedirect("StudentDashboard.jsp");
				return;
			}

			ParentDAO parentDAO = new ParentDAOImpl();
			Parent parent = parentDAO.login(userEmail, userPassword);

			if (parent != null) {
				session.setAttribute("userType", "parent");
				session.setAttribute("parentId", parent.getParentId());
				session.setAttribute("userName", parent.getParentName());
				session.setAttribute("studentId", parent.getStudentId());

				Student studentInfo = studentDAO.getStudentById(parent.getStudentId());

				if (studentInfo != null) {
					session.setAttribute("studentStandard", studentInfo.getStudentStandard());
					session.setAttribute("studentFee", studentInfo.getStudentFee());
				}

				System.out.println("Parent Logged In. Child Student ID: " + parent.getStudentId());

				res.sendRedirect("StudentDashboard.jsp");
				return;
			}

			session.setAttribute("LoginMessage", "Invalid Email or Password. Please try again.");
			res.sendRedirect("login.jsp");

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("LoginMessage", "An error occurred. Please try again later.");
			res.sendRedirect("login.jsp");
		}
	}
}
