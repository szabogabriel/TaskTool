package com.ibm.sk.tasktool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.sk.tasktool.entity.LogEntriesEntity;

public interface LogEntriesRepository extends JpaRepository<LogEntriesEntity, Long> {
    
}
