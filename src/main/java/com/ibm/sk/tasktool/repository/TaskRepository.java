package com.ibm.sk.tasktool.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ibm.sk.tasktool.entity.TaskEntity;
import com.ibm.sk.tasktool.repository.custom.TaskRepositoryCustom;

//TODO: liquibase inside the app
public interface TaskRepository extends JpaRepository<TaskEntity, Long>, TaskRepositoryCustom {

    Optional<TaskEntity> findByTaskId(Long taskId);

    @Query("SELECT t FROM TaskEntity t WHERE t.completed = :completed")
    List<TaskEntity> findByCompleted(boolean completed);

    List<TaskEntity> findByDueDateBefore(LocalDate dueDate);

}
