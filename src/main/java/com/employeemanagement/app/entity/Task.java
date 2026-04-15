package com.employeemanagement.app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * A work item that can be created by a manager and assigned to a subordinate.
 *
 * <p>Tasks move through three Kanban columns: {@code TODO → IN_PROGRESS → DONE}.
 */
@Entity
@Table(name = "tasks", indexes = {
        @Index(name = "idx_task_assignee",  columnList = "assignee_id"),
        @Index(name = "idx_task_creator",   columnList = "creator_id"),
        @Index(name = "idx_task_status",    columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private TaskStatus status = TaskStatus.TODO;

    /** The user who created / assigned this task. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    /** The user this task is assigned to. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;

    private LocalDateTime dueDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ── Kanban column enum ───────────────────────────────────────────────────

    public enum TaskStatus {
        TODO,
        IN_PROGRESS,
        DONE
    }
}
