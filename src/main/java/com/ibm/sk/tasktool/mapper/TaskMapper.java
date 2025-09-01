package com.ibm.sk.tasktool.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ibm.sk.tasktool.dto.Task;
import com.ibm.sk.tasktool.dto.TaskCreate;
import com.ibm.sk.tasktool.dto.TaskUpdate;
import com.ibm.sk.tasktool.entity.TaskEntity;

//TODO: add Mapstruct here!
public class TaskMapper {

    public static Task toDto(TaskEntity entity) {
        if (entity == null) {
            return null;
        }

        Task dto = new Task();
        dto.setTaskId(entity.getTaskId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setCompleted(entity.isCompleted());
        dto.setDueDate(entity.getDueDate());
        dto.setTags(TagMapper.toDtoStringList(entity.getTags()));
        return dto;
    }

    public static List<Task> toDtoList(List<TaskEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<Task> dtos = new ArrayList<>();
        for (TaskEntity entity : entities) {
            dtos.add(toDto(entity));
        }
        return dtos;
    }

    public static TaskEntity toNewEntity(TaskCreate dto) {
        if (dto == null) {
            return null;
        }

        TaskEntity entity = new TaskEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setCompleted(false);
        entity.setDueDate(toLocalDateTime(dto.getDueDate()));
        
        return entity;
    }

    public static TaskEntity updateEntity(TaskEntity entity, TaskUpdate update) {
        if (entity == null || update == null) {
            return entity;
        }

        if (update.getTitle() != null) {
            entity.setTitle(update.getTitle());
        }
        if (update.getDescription() != null) {
            entity.setDescription(update.getDescription());
        }
        entity.setCompleted(update.isCompleted());
        if (update.getDueDate() != null) {
            entity.setDueDate(toLocalDateTime(update.getDueDate()));
        }
        // Tags are not updated here

        return entity;
    }

    private static LocalDateTime toLocalDateTime(String date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
    }
}
