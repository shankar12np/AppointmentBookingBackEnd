package com.diyo.apointmentbookingsystem.Repository;

import com.diyo.apointmentbookingsystem.Entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
//
   Optional<Login> findByEmail(String email);

//    Login findByEmailAndUsername(String email, String userName);
}
