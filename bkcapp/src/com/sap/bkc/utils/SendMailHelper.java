package com.sap.bkc.utils;


import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendMailHelper {

	public static void sendMail(String receiverEmailId, String subject, String body) {
		
		final String username = AppConstants.MAILUSERNAME;
		final String password = AppConstants.MAILPASSWORD;
		
		Properties props = PropertyMailObject.getInstance();

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,password);
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmailId));
			message.setSubject(subject);
			message.setText(body);
			

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}


	}

}
