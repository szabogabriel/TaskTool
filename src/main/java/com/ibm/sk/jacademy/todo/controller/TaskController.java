package com.ibm.sk.jacademy.todo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ibm.sk.jacademy.todo.dto.Task;
import com.ibm.sk.jacademy.todo.dto.TaskCreate;
import com.ibm.sk.jacademy.todo.dto.TaskUpdate;
import com.ibm.sk.jacademy.todo.service.ControllerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/tasks", produces = "application/json")
public class TaskController {

  @Autowired
  private ControllerService service;

  @GetMapping
  public Page<Task> list(@RequestParam(required=false) Boolean done,
                            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE) LocalDate dueBefore,
                            @PageableDefault Pageable pageable) {
    List<Task> todos = service.findFiltered(done, dueBefore);
    
    int start = (int) pageable.getOffset();
    int end = Math.min((start + pageable.getPageSize()), todos.size());
    List<Task> pageContent = todos.subList(start, end);
    Page<Task> page = new org.springframework.data.domain.PageImpl<>(pageContent, pageable, todos.size());
    return page;
  }

  @PostMapping
  public Task add(@Valid @RequestBody TaskCreate todo) {
    return service.add(todo);
  }

  @PatchMapping("/{id}")
  public Task update(@PathVariable Long id, @Valid @RequestBody TaskUpdate todo) {
    Task ret = null;
    try {
      ret = service.update(id, todo);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return ret;
  }

  @DeleteMapping("/{id}")
  public Task delete(@PathVariable Long id) {
    Task ret = null;
    try {
      service.delete(id);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return ret;
  }
    
}
