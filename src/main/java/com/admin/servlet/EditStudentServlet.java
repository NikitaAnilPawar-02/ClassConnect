package com.admin.servlet;

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

@WebServlet("/admin/EditStudentServlet")
public class EditStudentServlet extends HttpServlet {
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

		try {
			int studentId = Integer.parseInt(request.getParameter("studentId"));
			String studentName = request.getParameter("studentName");
			String studentEmail = request.getParameter("studentEmail");
			String studentPhnNo = request.getParameter("studentPhnNo");
			int studentStandard = Integer
					.parseInt(request.getParameter("studentStandard"));

			String parentName = request.getParameter("parentName");
			String parentEmail = request.getParameter("parentEmail");
			String parentPhnNo = request.getParameter("parentPhnNo");
			String relationToStudent = request
					.getParameter("relationToStudent");

			Student student = new Student();
			student.setStudentId(studentId);
			student.setStudentName(studentName);
			student.setStudentEmail(studentEmail);
			student.setStudentPhnNo(studentPhnNo);
			student.setStudentStandard(studentStandard);

			Parent parent = new Parent();
			parent.setStudentId(studentId);
			parent.setParentName(parentName);
			parent.setParentEmail(parentEmail);
			parent.setParentPhnNo(parentPhnNo);
			parent.setRelationToStudent(relationToStudent);

			StudentDAO studentDAO = new StudentDAOImpl();
			boolean studentUpdated = studentDAO.updateStudent(student);

			ParentDAO parentDAO = new ParentDAOImpl();
			boolean parentUpdated = parentDAO.updateParent(parent);

			if (studentUpdated && parentUpdated) {
				request.getSession().setAttribute("successMsg",
						"Student and Parent Updated Successfully.");
			} else {
				request.getSession().setAttribute("errorMsg",
						"Update Failed. Please try again.");
			}
			response.sendRedirect("manageStudents.jsp");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
