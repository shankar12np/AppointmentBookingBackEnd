package com.diyo.apointmentbookingsystem.Controller;

import com.diyo.apointmentbookingsystem.Entity.Login;
import com.diyo.apointmentbookingsystem.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/loginAPI")
public class LoginController {
    @Autowired
    private LoginService loginService;

@PostMapping("/login")
public ResponseEntity<String> loginUser(@RequestBody Login login) {

    if (loginService.loginUser(login)) {
        return ResponseEntity.ok("true");
    } else {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        return ResponseEntity.ok("false");
    }
}
}