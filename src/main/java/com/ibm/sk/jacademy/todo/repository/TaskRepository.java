package com.ibm.sk.jacademy.todo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.sk.jacademy.todo.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> findByTaskId(Long taskId);

    List<TaskEntity> findByCompleted(boolean completed);

    List<TaskEntity> findByCompletedAndDueDateBefore(boolean completed, LocalDate dueDate);

    List<TaskEntity> findByDueDateBefore(LocalDate dueDate);

}
