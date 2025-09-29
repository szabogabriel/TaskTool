package com.ibm.sk.tasktool.repository.custom;

import java.time.LocalDate;
import java.util.List;

import com.ibm.sk.tasktool.entity.TaskEntity;

public interface TaskRepositoryCustom {

    List<TaskEntity> findByCompletedAndDueDateBefore(boolean completed, LocalDate dueDate);
    
}
