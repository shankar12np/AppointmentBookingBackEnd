package com.diyo.apointmentbookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:4200")
@EntityScan("com.diyo.apointmentbookingsystem.Entity")
public class ApointmentBookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApointmentBookingSystemApplication.class, args);
    }

}
