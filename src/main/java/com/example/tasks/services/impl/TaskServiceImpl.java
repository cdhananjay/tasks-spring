package com.example.tasks.services.impl;

import com.example.tasks.domain.entities.Task;
import com.example.tasks.domain.entities.TaskList;
import com.example.tasks.domain.entities.TaskPriority;
import com.example.tasks.domain.entities.TaskStatus;
import com.example.tasks.repositories.TaskListRepository;
import com.example.tasks.repositories.TaskRepository;
import com.example.tasks.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (task.getId() != null) {
            throw new IllegalArgumentException("Task already has an Id");
        }
        if (task.getTitle() == null) {
            throw new IllegalArgumentException("Task must have a title");
        }
        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(
                () -> new IllegalArgumentException("Invalid TaskList id.")
        );
        TaskPriority priority = task.getPriority();
        if (priority == null) priority = TaskPriority.MEDIUM;
        LocalDateTime now = LocalDateTime.now();
        return taskRepository.save(
                new Task(
                        null,
                        task.getTitle(),
                        task.getDescription(),
                        task.getDueDate(),
                        TaskStatus.OPEN,
                        priority,
                        taskList,
                        now,
                        now
                )
        );
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("Task requires an Id");
        }
        if (!taskId.equals(task.getId())) {
            throw new IllegalArgumentException("Task id cannot be updated.");
        }
        if (null == task.getTitle()) {
            throw new IllegalArgumentException("Task must have a title");
        }
        if (null == task.getPriority()) {
            throw new IllegalArgumentException("Task must have a priority");
        }
        if (null == task.getStatus()) {
            throw new IllegalArgumentException("Task must have a status");
        }
        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId).orElseThrow(
                () -> new IllegalArgumentException("Task not found.")
        );
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setUpdated(LocalDateTime.now());
        return taskRepository.save(existingTask);
    }

    @Override
    @Transactional
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
