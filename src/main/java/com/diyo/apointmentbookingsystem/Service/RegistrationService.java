package com.diyo.apointmentbookingsystem.Service;

import com.diyo.apointmentbookingsystem.Entity.Login;
import com.diyo.apointmentbookingsystem.Repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    @Autowired
    private LoginRepository loginRepository;

    public void registerUser(Login login){

        loginRepository.save(login);
    }
}
