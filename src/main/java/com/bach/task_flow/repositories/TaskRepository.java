package com.bach.task_flow.repositories;

import com.bach.task_flow.domains.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    Page<Task> findAllByProject_Id(UUID projectId, Pageable pageable);
    boolean existsByIdAndUsers_Username(UUID id, String users_username);

}
