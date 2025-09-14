package com.utils;

public class EmailServiceTest {
	public static void main(String[] args) {
		String recipient = "pawarnikita0293@gmail.com";
		String subject = "Test Email";
		String messageBody = "This is a test email sent using javax.mail.";

		boolean emailSent = EmailService.sendEmail(recipient, subject,
				messageBody);
		if (emailSent) {
			System.out.println("Email sent successfully!");
		} else {
			System.out.println("Failed to send email.");
		}
	}
}