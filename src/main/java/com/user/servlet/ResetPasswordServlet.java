package com.user.servlet;
import java.io.IOException;

import com.DAO.ParentDAOImpl;
import com.DAO.StudentDAOImpl;
import com.utils.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.getAttribute("otpVerified") == null) {
			res.sendRedirect("forgotPassword.jsp");
			return;
		}

		String email = (String) session.getAttribute("otpEmail");
		String userType = (String) session.getAttribute("otpUserType");
		String newPassword = PasswordUtil
				.hashPassword(req.getParameter("newPassword"));

		boolean updated = userType.equals("student")
				? new StudentDAOImpl().updatePassword(email, newPassword)
				: new ParentDAOImpl().updatePassword(email, newPassword);

		session.setAttribute("passwordUpdateMessage",
				updated
						? "Password updated successfully."
						: "Failed to update password.");
		res.sendRedirect("login.jsp");
	}
}
