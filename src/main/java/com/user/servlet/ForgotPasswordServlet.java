package com.user.servlet;

import java.io.IOException;
import java.security.SecureRandom;

import com.DAO.ParentDAO;
import com.DAO.ParentDAOImpl;
import com.DAO.StudentDAO;
import com.DAO.StudentDAOImpl;
import com.utils.EmailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		String userType = request.getParameter("userType");

		boolean isUserExists = false;
		if ("student".equals(userType)) {
			StudentDAO studentDAO = new StudentDAOImpl();
			isUserExists = studentDAO.checkEmailExists(email);
		} else if ("parent".equals(userType)) {
			ParentDAO parentDAO = new ParentDAOImpl();
			isUserExists = parentDAO.checkEmailExists(email);
		}

		if (isUserExists) {
			String otp = generateOTP();
			session.setAttribute("otp", otp);
			session.setAttribute("otpEmail", email);
			session.setAttribute("otpUserType", userType);

			String subject = "Your OTP for Password Reset";
			String messageBody = "Your OTP for password reset is: " + otp;

			if (EmailService.sendEmail(email, subject, messageBody)) {
				session.setAttribute("otpMessage",
						"OTP has been sent to your email.");
				response.sendRedirect("verifyOTP.jsp");
			} else {
				session.setAttribute("otpMessage",
						"Failed to send OTP. Try again later.");
				response.sendRedirect("forgotPassword.jsp");
			}
		} else {
			session.setAttribute("otpMessage",
					"Email not found or user type mismatch.");
			response.sendRedirect("forgotPassword.jsp");
		}
	}

	private String generateOTP() {
		SecureRandom random = new SecureRandom();
		int otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);
	}
}