package com.bach.task_flow.services.impl;

import com.bach.task_flow.repositories.ProjectRepository;
import com.bach.task_flow.repositories.TaskRepository;
import com.bach.task_flow.services.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    ProjectRepository projectRepository;



}
