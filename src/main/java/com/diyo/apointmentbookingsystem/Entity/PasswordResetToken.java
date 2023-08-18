package com.diyo.apointmentbookingsystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Data
@Table(name = "validation_info")
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private String newPassword;

    @OneToOne(targetEntity = Login.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Login user;

    private Date expiryDate;

    public boolean isExpired() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return currentDateTime.isAfter(expiryDateTime);
    }
public String getToken(){
        return token;
}
    public void setToken(String token) {
        this.token = token;
    }
}