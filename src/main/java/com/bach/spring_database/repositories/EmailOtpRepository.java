package com.bach.spring_database.repositories;

import com.bach.spring_database.domains.EmailOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.UUID;

public interface EmailOtpRepository extends JpaRepository<EmailOTP, UUID> {

    @Modifying
    void deleteByEmail(String email);
    Optional<EmailOTP> findByEmailAndOtp(String email, String otp);

}
