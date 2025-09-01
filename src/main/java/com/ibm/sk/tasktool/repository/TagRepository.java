package com.ibm.sk.tasktool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.sk.tasktool.entity.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    Optional<TagEntity> findByTagId(Long tagId);

    Optional<TagEntity> findByName(String name);
    
}
