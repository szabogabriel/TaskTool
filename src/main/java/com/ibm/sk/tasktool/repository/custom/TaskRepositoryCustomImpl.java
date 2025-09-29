package com.ibm.sk.tasktool.repository.custom;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ibm.sk.tasktool.entity.TaskEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class TaskRepositoryCustomImpl implements TaskRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TaskEntity> findByCompletedAndDueDateBefore(boolean completed, LocalDate dueDate) {
        String sql = "SELECT t FROM TaskEntity t WHERE t.completed = :completed AND t.dueDate < :dueDate";

        TypedQuery<TaskEntity> query = em.createQuery(sql, TaskEntity.class);
        query.setParameter("completed", completed);
        query.setParameter("dueDate", dueDate);
        
        return query.getResultList();
    }

}
