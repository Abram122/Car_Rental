package utils;

import dao.AdminDAO;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;

public class OtpService {
    private static final int OTP_LENGTH = 6;
    private static final int EXPIRY_MINUTES = 5;

    public static void generateAndSendOtp(String username, String email) throws Exception {
      
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < OTP_LENGTH; i++) {
            sb.append(rnd.nextInt(10));
        }
        String otp = sb.toString();

        Timestamp expiry = Timestamp.from(Instant.now().plusSeconds(EXPIRY_MINUTES * 60));

   
        AdminDAO dao = new AdminDAO();
        dao.saveOtp(username, otp, expiry);

        EmailUtil.sendOtpEmail(email, otp);
    }
}
