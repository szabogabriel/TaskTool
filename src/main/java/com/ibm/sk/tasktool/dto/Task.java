package com.ibm.sk.tasktool.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

//TODO: create one DTO without Lombok
@Data
public class Task {
    private Long taskId;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime dueDate;
    private PriorityEnum priority;
    private List<String> tags;
}
