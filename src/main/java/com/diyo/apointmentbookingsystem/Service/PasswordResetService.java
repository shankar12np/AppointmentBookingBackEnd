package com.diyo.apointmentbookingsystem.Service;

import com.diyo.apointmentbookingsystem.Entity.Login;
import com.diyo.apointmentbookingsystem.Entity.PasswordResetToken;
import com.diyo.apointmentbookingsystem.Repository.LoginRepository;
import com.diyo.apointmentbookingsystem.Repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@Service
public class PasswordResetService {
    private static final int TOKEN_LENGTH = 16;
    private static final int TOKEN_EXPIRY_HOURS = 24;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private LoginRepository loginRepository;
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
        String tokenValue = generateRandomToken();
        LocalDateTime expiryDateTime = LocalDateTime.now().plusHours(TOKEN_EXPIRY_HOURS);
        Date expiryDate = Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant());

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(tokenValue);
        token.setExpiryDate(expiryDate);
        token.setUser(user);

        tokenRepository.save(token);
        return token;
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
