package com.ibm.sk.tasktool.dto;

import lombok.Data;

@Data
public class TaskCreate {

    private String title;
    private String description;
    private boolean completed;
    private String dueDate; // ISO 8601 format
    private java.util.List<String> tags;
    
}
