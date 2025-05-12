package com.bach.task_flow.repositories;

import com.bach.task_flow.domains.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
