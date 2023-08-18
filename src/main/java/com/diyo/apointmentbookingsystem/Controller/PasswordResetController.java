package com.diyo.apointmentbookingsystem.Controller;

import com.diyo.apointmentbookingsystem.Entity.Login;
import com.diyo.apointmentbookingsystem.Entity.PasswordResetToken;
import com.diyo.apointmentbookingsystem.Service.LoginService;
import com.diyo.apointmentbookingsystem.Service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/password-reset")
public class PasswordResetController {
    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private LoginService loginService;

@Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetToken resetRequest) {
        String token = resetRequest.getToken();
        String newPassword = resetRequest.getNewPassword();
        boolean resetSuccess = passwordResetService.resetPasswordForUser(token, newPassword);

        if (resetSuccess) {
            return ResponseEntity.ok("Password reset successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
    }
    @PostMapping("/request-token")
    public ResponseEntity<String> requestToken(@RequestParam String email) {
        Login user = loginService.findByEmail(email);

        if (user != null) {
            PasswordResetToken token = passwordResetService.generateTokenForUser(user);

            // Generate the email content
            String emailSubject = "Password Reset Token";
            String emailBody = "Your password reset token is: " + token.getToken();

            // Send the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject(emailSubject);
            mailMessage.setText(emailBody);
            javaMailSender.send(mailMessage);

            return ResponseEntity.ok("Token generated and sent to user");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }


}