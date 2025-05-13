package com.bach.task_flow.repositories;

import com.bach.task_flow.domains.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Page<Project> findAllByMembers_UsernameOrManager_Username(Pageable pageable, String memberName, String managerName);
    boolean existsByIdAndMembers_Username(UUID id, String memberName);

}
