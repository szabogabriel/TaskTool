package com.ibm.sk.jacademy.todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.sk.jacademy.todo.entity.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    Optional<TagEntity> findByTagId(Long tagId);

    Optional<TagEntity> findByName(String name);
    
}
