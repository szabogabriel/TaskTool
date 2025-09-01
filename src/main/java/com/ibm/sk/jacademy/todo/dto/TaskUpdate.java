package com.ibm.sk.jacademy.todo.dto;

import lombok.Data;

@Data
public class TaskUpdate {

    private String title;
    private String description;
    private boolean completed;
    private String dueDate; // ISO 8601 format
    private java.util.List<String> tags;
    
}
