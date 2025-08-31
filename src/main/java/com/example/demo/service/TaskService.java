package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Task createTask(Task task) {
        if (task.getRunAt() == null) task.setRunAt(Instant.now());
        if (task.getStatus() == null) task.setStatus("PENDING");
        if (task.getAttempts() == null) task.setAttempts(0);
        if (task.getMaxAttempts() == null) task.setMaxAttempts(5);
        return repo.save(task);
    }

    @Transactional(readOnly = true)
    public Optional<Task> getTask(UUID id) {
        return repo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    @Transactional
    public Task updateStatus(UUID id, String newStatus) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
        task.updateStatus(newStatus);
        return repo.save(task);
    }

    @Transactional
    public void deleteTask(UUID id) {
        repo.deleteById(id);
    }
}