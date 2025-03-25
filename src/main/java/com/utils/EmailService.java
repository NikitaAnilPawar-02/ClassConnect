package com.utils;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

	private static final String SMTP_HOST = "smtp.gmail.com";
	private static final String SMTP_PORT = "587";
	private static final String EMAIL_FROM = "pawarnikita0293@gmail.com";
	private static final String EMAIL_PASSWORD = "eijz jbfz kumo mqsu";

	public static boolean sendEmail(String recipient, String subject,
			String messageBody) {
		try {
			System.out.println("Preparing to send email to: " + recipient);
			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", SMTP_HOST);
			properties.put("mail.smtp.port", SMTP_PORT);

			Session session = Session.getInstance(properties,
					new Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(EMAIL_FROM,
									EMAIL_PASSWORD);
						}
					});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(EMAIL_FROM));
			message.setRecipient(Message.RecipientType.TO,
					new InternetAddress(recipient));
			message.setSubject(subject);
			message.setText(messageBody);

			Transport.send(message);
			System.out.println("Email successfully sent to: " + recipient);
			return true;
		} catch (MessagingException e) {
			System.err.println("Failed to send email to: " + recipient);
			e.printStackTrace();
			return false;
		}
	}
}