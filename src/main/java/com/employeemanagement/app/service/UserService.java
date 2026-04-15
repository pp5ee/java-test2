package com.employeemanagement.app.service;

import com.employeemanagement.app.dto.UserDto;
import com.employeemanagement.app.entity.User;
import com.employeemanagement.app.exception.BadRequestException;
import com.employeemanagement.app.exception.ResourceNotFoundException;
import com.employeemanagement.app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToDto(user);
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return mapToDto(user);
    }

    public List<UserDto> getUsersByManager(Long managerId) {
        return userRepository.findByManagerId(managerId).stream()
                .map(this::mapToDto)
                .toList();
    }

    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getEmail())); // Temporary password
        user.setRole(userDto.getRole());
        if (userDto.getManagerId() != null) {
            User manager = userRepository.findById(userDto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + userDto.getManagerId()));
            user.setManager(manager);
        }
        user.setActive(true);
        user.setEmailVerified(false);

        User saved = userRepository.save(user);
        return mapToDto(saved);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (userDto.getFullName() != null) {
            user.setFullName(userDto.getFullName());
        }
        if (userDto.getRole() != null) {
            user.setRole(userDto.getRole());
        }
        if (userDto.getManagerId() != null) {
            if (userDto.getManagerId().equals(id)) {
                throw new BadRequestException("A user cannot be their own manager");
            }
            User manager = userRepository.findById(userDto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + userDto.getManagerId()));
            if (wouldCreateCircularReference(user, manager)) {
                throw new BadRequestException("Setting this manager would create a circular reference");
            }
            user.setManager(manager);
        }
        if (userDto.isActive() != user.isActive()) {
            user.setActive(userDto.isActive());
        }

        User updated = userRepository.save(user);
        return mapToDto(updated);
    }

    private boolean wouldCreateCircularReference(User user, User potentialManager) {
        User current = potentialManager;
        while (current != null) {
            if (current.getId().equals(user.getId())) {
                return true;
            }
            current = current.getManager();
        }
        return false;
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .managerId(user.getManager() != null ? user.getManager().getId() : null)
                .managerName(user.getManager() != null ? user.getManager().getFullName() : null)
                .emailVerified(user.isEmailVerified())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .build();
    }
}