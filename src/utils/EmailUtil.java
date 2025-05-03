package utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.InputStream;
import java.util.Properties;

public class EmailUtil {
    private static final Properties config = new Properties();

    static {
        try (InputStream input = EmailUtil.class.getClassLoader().getResourceAsStream("resources/smtp.properties")) {
            if (input == null) {
                throw new RuntimeException("smtp.properties file not found in classpath under resources/");
            }
            config.load(input);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load smtp.properties from classpath: " + ex.getMessage(), ex);
        }
    }

    public static void sendOtpEmail(String email,String otp) throws MessagingException {
        String host = config.getProperty("smtp.host");
        String user = config.getProperty("smtp.user");
        String pass = config.getProperty("smtp.pass");
        String port = config.getProperty("smtp.port", "587");

        if (host == null || user == null || pass == null) {
            throw new MessagingException("Missing SMTP configuration (host, user, or pass)");
        }

        System.out.println("Sending OTP to: " + email);
        System.out.println("SMTP Config - Host: " + host + ", User: " + user + ", Port: " + port);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.debug", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(user));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            msg.setSubject("Your Admin Login OTP");
            msg.setText("Your one-time login code is: " + otp + "\nIt expires in 5 minutes.");
            Transport.send(msg);
            System.out.println("OTP email sent successfully.");
        } catch (MessagingException ex) {
            System.err.println("Failed to send OTP email: " + ex.getMessage());
            throw new MessagingException("Failed to send OTP email: " + ex.getMessage(), ex);
        }
    }
}
