package com.user.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.DB.DBConnect;
import com.entity.Parent;
import com.entity.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
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
		if (studentId == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		Student student = new Student();
		Parent parent = new Parent();

		try (Connection con = DBConnect.getConn()) {
			String studentQuery = "SELECT * FROM student WHERE studentId = ?";
			try (PreparedStatement stmt = con.prepareStatement(studentQuery)) {
				stmt.setInt(1, studentId);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						student.setStudentId(rs.getInt("studentId"));
						student.setStudentName(rs.getString("studentName"));
						student.setStudentEmail(rs.getString("studentEmail"));
						student.setStudentPhnNo(rs.getString("studentPhnNo"));
						student.setStudentGender(rs.getString("studentGender"));
						student.setStudentDOB(
								rs.getDate("studentDOB").toLocalDate());
						student.setStudentAddress(
								rs.getString("studentAddress"));
						student.setStudentStandard(
								rs.getInt("studentStandard"));
						student.setStudentFee(rs.getDouble("studentFee"));
					}
				}
			}

			String parentQuery = "SELECT * FROM parent WHERE studentId = ?";
			try (PreparedStatement stmt = con.prepareStatement(parentQuery)) {
				stmt.setInt(1, studentId);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						parent.setParentId(rs.getInt("parentId"));
						parent.setParentName(rs.getString("parentName"));
						parent.setParentEmail(rs.getString("parentEmail"));
						parent.setParentPhnNo(rs.getString("parentPhnNo"));
						parent.setRelationToStudent(
								rs.getString("relationToStudent"));
					} else {
						System.out.println(
								"Parent details not found for studentId: "
										+ studentId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		student.setParent(parent);
		request.setAttribute("student", student);
		request.setAttribute("parent", parent);
		request.getRequestDispatcher("profile.jsp").forward(request, response);
	}
}