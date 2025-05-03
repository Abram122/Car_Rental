package utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtil {
    private static final String SMTP_HOST = "smtp.example.com";
    private static final String SMTP_USER = "not yet decided email wh will use fake one in testing@example.com";//needs to be replaced 
    private static final String SMTP_PASS = "your-smtp-password";//will replace it too

    public static void sendOtpEmail(String toAddress, String otp) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASS);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(SMTP_USER));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        msg.setSubject("Your Admin Login OTP");
        msg.setText("Your one‑time login code is: " + otp
                    + "\nIt expires in 5 minutes.");
        Transport.send(msg);
    }
}
