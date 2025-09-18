package com.franciscoabsl.notification.adapter.out.smtp;
import com.franciscoabsl.notification.domain.EmailMessage;
import com.franciscoabsl.notification.port.out.EmailSenderPortOut;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SmtpEmailSender implements EmailSenderPortOut {

    private final String host;
    private final int port;
    private final String from;

    public SmtpEmailSender(String host, int port, String from) {
        this.host = host;
        this.port = port;
        this.from = from;
    }

    public boolean sendEmail(EmailMessage message) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "false"); // sem autenticação
        props.put("mail.smtp.starttls.enable", "false"); // Mailhog não precisa
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));

        Session session = Session.getInstance(props);

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(message.getTo()));
            mimeMessage.setSubject(message.getSubject());
            mimeMessage.setText(message.getBody());

            Transport.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}