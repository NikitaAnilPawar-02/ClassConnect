package com.admin.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.DAO.StudentDAO;
import com.DAO.StudentDAOImpl;
import com.entity.Parent;
import com.entity.Student;
import com.utils.EmailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/AddStudentServlet")
public class AddStudentServlet extends HttpServlet {
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
			String studentName = request.getParameter("studentName");
			String studentEmail = request.getParameter("studentEmail");
			String studentPhnNo = request.getParameter("studentPhnNo");
			String studentGender = request.getParameter("studentGender");
			LocalDate studentDOB = LocalDate
					.parse(request.getParameter("studentDOB"));
			String studentAddress = request.getParameter("studentAddress");
			int studentStandard = Integer
					.parseInt(request.getParameter("studentStandard"));
			Double studentFee = Double
					.parseDouble(request.getParameter("studentFee"));

			String parentName = request.getParameter("parentName");
			String parentEmail = request.getParameter("parentEmail");
			String parentPhnNo = request.getParameter("parentPhnNo");
			String relationToStudent = request
					.getParameter("relationToStudent");

			String studentPassword = generatePassword(studentName, studentDOB);
			String parentPassword = generatePassword(parentName, studentDOB);

			Student student = new Student();
			student.setStudentName(studentName);
			student.setStudentEmail(studentEmail);
			student.setStudentPassword(studentPassword);
			student.setStudentPhnNo(studentPhnNo);
			student.setStudentGender(studentGender);
			student.setStudentDOB(studentDOB);
			student.setStudentAddress(studentAddress);
			student.setStudentStandard(studentStandard);
			student.setStudentFee(studentFee);

			Parent parent = new Parent();
			parent.setParentName(parentName);
			parent.setParentPassword(parentPassword);
			parent.setParentEmail(parentEmail);
			parent.setParentPhnNo(parentPhnNo);
			parent.setRelationToStudent(relationToStudent);

			StudentDAO studentDAO = new StudentDAOImpl();
			boolean isAdded = studentDAO.addStudent(student, parent);

			if (isAdded) {
				System.out.println(
						"Student and Parent added successfully. Sending emails...");

				String studentMessage = "Dear " + studentName
						+ ", your login credentials are:\n" + "Email: "
						+ studentEmail + "\nPassword: " + studentPassword;
				String parentMessage = "Dear " + parentName
						+ ", your login credentials are:\n" + "Email: "
						+ parentEmail + "\nPassword: " + parentPassword;

				boolean studentEmailSent = EmailService.sendEmail(studentEmail,
						"ClassConnect Student Login Credentials",
						studentMessage);
				boolean parentEmailSent = EmailService.sendEmail(parentEmail,
						"ClassConnect Parent Login Credentials", parentMessage);

				if (studentEmailSent && parentEmailSent) {
					session.setAttribute("successMsg",
							"Student and Parent added successfully. Login credentials sent via email.");
				} else {
					session.setAttribute("warningMsg",
							"Student and Parent added, but failed to send one or more emails.");
				}
			} else {
				session.setAttribute("errorMsg",
						"Failed to add Student and Parent. Please try again.");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			session.setAttribute("errorMsg",
					"Email already exists. Please use a different email.");
		} catch (SQLException e) {
			if (e.getMessage().contains("studentEmail")) {
				session.setAttribute("errorMsg",
						"Student email already exists. Please use a different email.");
			} else if (e.getMessage().contains("parentEmail")) {
				session.setAttribute("errorMsg",
						"Parent email already exists. Please use a different email.");
			} else {
				session.setAttribute("errorMsg",
						"Database error: " + e.getMessage());
			}
		} catch (Exception e) {
			session.setAttribute("errorMsg",
					"Failed to add Student and Parent. Please try again.");
			e.printStackTrace();
		}

		response.sendRedirect("addStudent.jsp");
	}

	private String generatePassword(String name, LocalDate dob) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
		String dobPart = dob.format(formatter);
		String namePart = name.length() >= 4
				? name.substring(0, 4).toLowerCase()
				: name.toLowerCase();
		return namePart + dobPart;
	}
}