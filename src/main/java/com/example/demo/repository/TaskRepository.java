package com.example.demo.repository;

import com.example.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.time.Instant;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    // Find top N pending tasks that are due, ordered by runAt ascending
    List<Task> findTop10ByStatusAndRunAtBeforeOrderByRunAtAsc(String status, Instant runAt);

    // Or generic version to allow a limit:
    List<Task> findTopByStatusAndRunAtBeforeOrderByRunAtAsc(String status, Instant runAt);
}
