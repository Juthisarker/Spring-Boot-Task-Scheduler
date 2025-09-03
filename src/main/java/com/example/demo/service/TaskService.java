package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Lombok: generates constructor for final fields
public class TaskService {

    private final TaskRepository repo;

    @Transactional
    public Task createTask(Task task) {
        // JPA @PrePersist will already set defaults,
        // so here you don’t need to duplicate unless you want overrides.
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

    // -------------------------
    // Extra methods for scheduler
    // -------------------------

//    @Transactional(readOnly = true)
//    public List<Task> getDueTasks(int limit) {
//        return repo.findTop10ByStatusAndRunAtBeforeOrderByRunAtAsc("PENDING", Instant.now());
//    }

    @Transactional(readOnly = true)
    public List<Task> getDueTasks(int limit) {
        // Currently hardcoded to top 10
        // You can modify repository method to accept dynamic limit if needed
        return repo.findTop10ByStatusAndRunAtBeforeOrderByRunAtAsc("PENDING", Instant.now());
    }

    @Transactional
    public Task updateTask(Task task) {
        return repo.save(task);
    }

    @Transactional
    public Task incrementAttempts(UUID id) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
        task.incrementAttempts();
        return repo.save(task);
    }
}
