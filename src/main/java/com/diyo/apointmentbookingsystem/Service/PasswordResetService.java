package com.diyo.apointmentbookingsystem.Service;

import com.diyo.apointmentbookingsystem.Entity.Login;
import com.diyo.apointmentbookingsystem.Entity.PasswordResetToken;
import com.diyo.apointmentbookingsystem.Repository.LoginRepository;
import com.diyo.apointmentbookingsystem.Repository.PasswordResetTokenRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetService {
    private static final int TOKEN_LENGTH = 16;
    private static final int TOKEN_EXPIRY_HOURS = 24;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private LoginRepository loginRepository;

    // Inject Twilio configuration values
    @Value("${twilio.accountSid}")
    private String twilioAccountSid;

    @Value("${twilio.authToken}")
    private String twilioAuthToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    public boolean resetPasswordForUser(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        if (resetToken == null || resetToken.isExpired()) {
            return false;
        }

        Login user = resetToken.getUser();
        if (user != null) {
            user.setPassword(newPassword);
            loginRepository.save(user);
            tokenRepository.delete(resetToken);
            return true;
        }

        return false;
    }

    public PasswordResetToken generateTokenForUser(Login user) {
        Optional<PasswordResetToken> existingTokenOptional = tokenRepository.findByUserEmail(user.getEmail());

        if (existingTokenOptional.isPresent()) {
            PasswordResetToken existingToken = existingTokenOptional.get();
            // Update the existing token's expiration date and other attributes
            LocalDateTime expiryDateTime = LocalDateTime.now().plusHours(TOKEN_EXPIRY_HOURS);
            Date expiryDate = Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant());

            existingToken.setToken(generateRandomToken()); // Generate a new token value
            existingToken.setExpiryDate(expiryDate);
            tokenRepository.save(existingToken);

            return existingToken;
        }

        String tokenValue = generateRandomToken();
        LocalDateTime expiryDateTime = LocalDateTime.now().plusHours(TOKEN_EXPIRY_HOURS);
        Date expiryDate = Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant());

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(tokenValue);
        token.setExpiryDate(expiryDate);
        token.setUser(user);

        tokenRepository.save(token);

        // Send the SMS using Twilio
        sendPasswordResetSms(user.getPhoneNumber(), tokenValue);

        return token;
    }

    private void sendPasswordResetSms(String phoneNumber, String tokenValue) {
        // Initialize Twilio
        Twilio.init(twilioAccountSid, twilioAuthToken);

        // Generate the SMS content
        String smsBody = "Your password reset token is: " + tokenValue;

        // Send the SMS
        Message.creator(
                new com.twilio.type.PhoneNumber("+" + phoneNumber), // User's phone number
                new com.twilio.type.PhoneNumber("+" + twilioPhoneNumber), // Your Twilio phone number
                smsBody
        ).create();
    }

    private String generateRandomToken() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token.append(characters.charAt(random.nextInt(characters.length())));
        }
        return token.toString();
    }
}
