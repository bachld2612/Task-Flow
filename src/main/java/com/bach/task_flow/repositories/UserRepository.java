package com.bach.task_flow.repositories;

import com.bach.task_flow.domains.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    @Modifying
    void deleteByUsername(String username);
    Set<User> findAllByUsernameIn(Set<String> usernames);
    Page<User> findAllByProjects_Id(UUID projectId, Pageable pageable);
    Page<User> findAllByTasks_Id(UUID taskId, Pageable pageable);

}
