package com.ibm.sk.tasktool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.sk.tasktool.dto.Tag;
import com.ibm.sk.tasktool.service.ControllerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/tags", produces = "application/json")
public class TagController {
    
    @Autowired
    private ControllerService service;
    
    @GetMapping
    public Page<Tag> list(@RequestParam(required=false) Boolean active,
                            @RequestParam(required=false) String name,
                            @RequestParam(required=false) String pattern,
                            @PageableDefault Pageable pageable) {
        List<Tag> tags = service.findTags(active, name, pattern);
        
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), tags.size());
        List<Tag> pageContent = tags.subList(start, end);
        Page<Tag> page = new org.springframework.data.domain.PageImpl<>(pageContent, pageable, tags.size());
        return page;
    }

    @GetMapping("/{id}")
    public Tag get(@PathVariable Long id) {
        return service.findTagById(id);
    }

    @PostMapping
    public Tag add(@Valid @RequestBody String tag) {
        return service.createTag(tag);
    }

    @DeleteMapping("/{id}")
    public Tag delete(@PathVariable Long id) {
        return service.deleteTag(id);
    }

}
