package com.employeemanagement.app.repository;

import com.employeemanagement.app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.assignee.id = :userId")
    List<Task> findByAssigneeId(@Param("userId") Long userId);

    @Query("SELECT t FROM Task t WHERE t.creator.id = :userId")
    List<Task> findByCreatorId(@Param("userId") Long userId);

    @Query("SELECT t FROM Task t WHERE t.assignee.id = :userId OR t.creator.id = :userId")
    List<Task> findByUserInvolved(@Param("userId") Long userId);
}