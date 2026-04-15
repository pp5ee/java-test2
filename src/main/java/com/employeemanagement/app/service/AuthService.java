package com.employeemanagement.app.service;

import com.employeemanagement.app.dto.AuthResponse;
import com.employeemanagement.app.dto.LoginRequest;
import com.employeemanagement.app.dto.RegisterRequest;
import com.employeemanagement.app.entity.User;
import com.employeemanagement.app.entity.VerificationCode;
import com.employeemanagement.app.exception.BadRequestException;
import com.employeemanagement.app.exception.ResourceNotFoundException;
import com.employeemanagement.app.repository.UserRepository;
import com.employeemanagement.app.repository.VerificationCodeRepository;
import com.employeemanagement.app.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository,
                       VerificationCodeRepository verificationCodeRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.verificationCodeRepository = verificationCodeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.EMPLOYEE);
        user.setActive(true);
        user.setEmailVerified(false);

        User saved = userRepository.save(user);

        // Generate and send verification code
        String code = generateVerificationCode();
        saveVerificationCode(saved, code);
        emailService.sendVerificationCode(saved.getEmail(), code);

        String token = jwtUtils.generateToken(saved);
        return buildAuthResponse(token, saved);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        if (!user.isActive()) {
            throw new BadRequestException("Account is disabled");
        }

        String token = jwtUtils.generateToken(user);
        return buildAuthResponse(token, user);
    }

    public void verifyEmail(String email, String code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        VerificationCode verificationCode = verificationCodeRepository
                .findTopByUserAndTypeOrderByCreatedAtDesc(user, VerificationCode.VerificationType.EMAIL)
                .orElseThrow(() -> new BadRequestException("No verification code found"));

        if (verificationCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Verification code has expired");
        }

        if (!verificationCode.getCode().equals(code)) {
            throw new BadRequestException("Invalid verification code");
        }

        user.setEmailVerified(true);
        userRepository.save(user);
        verificationCodeRepository.delete(verificationCode);
    }

    public void resendVerificationCode(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (user.isEmailVerified()) {
            throw new BadRequestException("Email is already verified");
        }

        String code = generateVerificationCode();
        saveVerificationCode(user, code);
        emailService.sendVerificationCode(user.getEmail(), code);
    }

    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        String code = generateVerificationCode();
        saveVerificationCode(user, code);
        emailService.sendPasswordResetCode(user.getEmail(), code);
    }

    public void resetPassword(String email, String code, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        VerificationCode verificationCode = verificationCodeRepository
                .findTopByUserAndTypeOrderByCreatedAtDesc(user, VerificationCode.VerificationType.PASSWORD_RESET)
                .orElseThrow(() -> new BadRequestException("No verification code found"));

        if (verificationCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Verification code has expired");
        }

        if (!verificationCode.getCode().equals(code)) {
            throw new BadRequestException("Invalid verification code");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        verificationCodeRepository.delete(verificationCode);
    }

    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private void saveVerificationCode(User user, String code) {
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setUser(user);
        verificationCode.setCode(code);
        verificationCode.setType(VerificationCode.VerificationType.EMAIL);
        verificationCode.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        verificationCodeRepository.save(verificationCode);
    }

    private AuthResponse buildAuthResponse(String token, User user) {
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .emailVerified(user.isEmailVerified())
                .build();
    }
}