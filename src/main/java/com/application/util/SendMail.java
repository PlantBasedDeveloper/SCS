package com.application.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * This class provides helper methods to send emails.
 *
 * @author Xhoni Robo
 */

public abstract class SendMail {

	// account credentials for the mail server
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 465;
    private static final String SMTP_AUTH_USER = "falldetectionscsgroupa@gmail.com";
    private static final String SMTP_AUTH_PWD  = "ihavefallenandicantgetup";

    /**
     * Sends an email to a single recipient using the static account credentials.
     *
     * @param messageBody The email's body.
     * @param msgSubject The email's subject.
     * @param toAddress The recipient's address
     * @throws Exception
     */
    public static void send(String messageBody, String msgSubject, String toAddress) throws Exception{
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");

        Session mailSession = Session.getDefaultInstance(props);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(SMTP_AUTH_USER));
        message.setSubject(msgSubject);
        message.setContent(messageBody + System.currentTimeMillis(), "text/plain");

        message.addRecipient(Message.RecipientType.TO,
             new InternetAddress(toAddress));

        transport.connect
          (SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

        transport.sendMessage(message,
           message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}