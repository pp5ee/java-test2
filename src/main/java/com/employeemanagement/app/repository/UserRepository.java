package com.employeemanagement.app.repository;

import com.employeemanagement.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.managerId = :managerId AND u.active = true")
    List<User> findSubordinates(@Param("managerId") Long managerId);

    @Query("SELECT u FROM User u WHERE u.role = :role AND u.active = true")
    List<User> findByRole(@Param("role") com.employeemanagement.app.entity.Role role);
}