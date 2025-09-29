package com.ibm.sk.tasktool.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.sk.tasktool.entity.LogEntriesEntity;
import com.ibm.sk.tasktool.repository.LogEntriesRepository;

@RestController
public class HelloController {

    @Autowired
    private LogEntriesRepository logEntriesRepository;

    @GetMapping("/hello")
    public String hello(@RequestHeader Map<String,String> headers) {
        logMessage("Hello endpoint was called." + headers.toString());
        logMessage("Returning greeting message.");
        return "Hello, World!";
    }

    private void logMessage(String message) {
        LogEntriesEntity logEntry = new LogEntriesEntity();
        logEntry.setMessage(message);
        logEntry.setTimestamp(java.time.LocalDateTime.now().toString());
        logEntry = logEntriesRepository.save(logEntry);
        System.out.println("Logged message with ID " + logEntry.getId() + ": " + message);
    }

}
