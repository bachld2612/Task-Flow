package com.bach.spring_database.repositories;

import com.bach.spring_database.domains.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
