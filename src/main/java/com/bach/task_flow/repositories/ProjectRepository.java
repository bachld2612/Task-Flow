package com.bach.task_flow.repositories;

import com.bach.task_flow.domains.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
