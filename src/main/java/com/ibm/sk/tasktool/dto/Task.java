package com.ibm.sk.tasktool.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Task {
    private Long taskId;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime dueDate;
    private List<String> tags;
}
