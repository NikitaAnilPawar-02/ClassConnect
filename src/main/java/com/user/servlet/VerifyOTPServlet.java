package com.user.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/VerifyOTPServlet")
public class VerifyOTPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String enteredOTP = req.getParameter("otp");
		String sessionOTP = (String) session.getAttribute("otp");

		if (enteredOTP != null && enteredOTP.equals(sessionOTP)) {
			session.setAttribute("otpVerified", true);
			res.sendRedirect("resetPassword.jsp");
		} else {
			session.setAttribute("otpVerifyMessage", "Invalid OTP. Try again.");
			res.sendRedirect("verifyOTP.jsp");
		}
	}
}
