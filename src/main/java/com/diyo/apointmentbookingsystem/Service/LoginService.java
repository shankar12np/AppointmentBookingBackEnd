package com.diyo.apointmentbookingsystem.Service;

import com.diyo.apointmentbookingsystem.Entity.Login;
import com.diyo.apointmentbookingsystem.Repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    public boolean loginUser(Login login) {
        String email = login.getEmail();
        String password = login.getPassword();

        // Retrieve user from the database based on the email
        Optional<Login> optionalUser = loginRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            Login user = optionalUser.get();
            String storedPassword = user.getPassword();

            // Compare the provided password with the stored password
            if (password.equals(storedPassword)) {
                return true; // Authentication succeeded
            }
        }

        return false; // Authentication failed
    }

    public Login findByEmail(String email) {
        return loginRepository.findByEmail(email).orElse(null);
    }
}
