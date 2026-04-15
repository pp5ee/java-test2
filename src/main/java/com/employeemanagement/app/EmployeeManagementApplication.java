package com.employeemanagement.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Entry point for the Employee Management System.
 *
 * <p>The application exposes REST APIs for employee registration, authentication,
 * organisational hierarchy management, and a task Kanban board. The compiled
 * React frontend is served from the same port via Spring's static-resource
 * handler, so no separate web server is required.
 *
 * <p>Async processing is enabled to support non-blocking email dispatch.
 */
@SpringBootApplication
@EnableAsync
public class EmployeeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }
}
