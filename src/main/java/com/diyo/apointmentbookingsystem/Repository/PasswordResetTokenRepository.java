package com.diyo.apointmentbookingsystem.Repository;

import com.diyo.apointmentbookingsystem.Entity.Login;
import com.diyo.apointmentbookingsystem.Entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    Optional<PasswordResetToken> findByUserEmail(String userEmail);

}

