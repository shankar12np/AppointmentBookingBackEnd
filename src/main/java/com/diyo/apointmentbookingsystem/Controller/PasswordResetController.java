package com.diyo.apointmentbookingsystem.Controller;

import com.diyo.apointmentbookingsystem.Entity.Login;
import com.diyo.apointmentbookingsystem.Entity.PasswordResetToken;
import com.diyo.apointmentbookingsystem.Service.LoginService;
import com.diyo.apointmentbookingsystem.Service.PasswordResetService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/password-reset")
@ControllerAdvice
public class PasswordResetController {
    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private LoginService loginService;

    // Inject Twilio configuration values
    @Value("${twilio.accountSid}")
    private String twilioAccountSid;

    @Value("${twilio.authToken}")
    private String twilioAuthToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;



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
    @PostMapping( "/request-token")
    public ResponseEntity<String> requestToken(@RequestParam String phoneNumber) {
        Login user = loginService.findByPhoneNumber(phoneNumber);

        if (user != null) {
            PasswordResetToken token = passwordResetService.generateTokenForUser(user);

            // Generate the SMS content
            String smsBody = "Your password reset token is: " + token.getToken();

            // Initialize Twilio
            Twilio.init(twilioAccountSid, twilioAuthToken);

            // Send the SMS
            Message.creator(
                    new PhoneNumber(user.getPhoneNumber()), // User's phone number
                    new PhoneNumber(twilioPhoneNumber),     // Your Twilio phone number
                    smsBody
            ).create();

            return ResponseEntity.ok("Token generated and sent to user"); // Modify the response here
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }
}