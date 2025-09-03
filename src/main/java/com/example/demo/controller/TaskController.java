package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService service;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task created = service.createTask(task);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Task> getTask(@PathVariable UUID id) {
        Optional<Task> task = service.getTask(id);
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Task>>> getAllTasks() {
        List<Task> tasks = service.getAllTasks();
        ApiResponse<List<Task>> response = new ApiResponse<>(true, "Fetched All tasks", tasks);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Task> updateStatus(@PathVariable UUID id,
                                             @RequestParam String status) {
        Task updated = service.updateStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
