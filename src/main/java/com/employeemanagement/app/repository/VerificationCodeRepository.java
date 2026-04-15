package com.employeemanagement.app.repository;

import com.employeemanagement.app.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    @Query("SELECT v FROM VerificationCode v WHERE v.email = :email AND v.type = :type AND v.used = false AND v.expiresAt > :now ORDER BY v.createdAt DESC")
    Optional<VerificationCode> findValidCode(
            @Param("email") String email,
            @Param("type") VerificationCode.CodeType type,
            @Param("now") LocalDateTime now);

    @Modifying
    @Query("UPDATE VerificationCode v SET v.used = true WHERE v.email = :email AND v.type = :type")
    void markAsUsed(
            @Param("email") String email,
            @Param("type") VerificationCode.CodeType type);
}