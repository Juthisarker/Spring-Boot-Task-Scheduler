package com.example.demo.service;
import com.example.demo.worker.Dispatcher;
import com.example.demo.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisScheduler {

    private final TaskService taskService;
    private final Dispatcher dispatcher;

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void scheduleDueTasks() {
        List<Task> dueTasks = taskService.getDueTasks(10); // get top 10 pending tasks
        for (Task task : dueTasks) {
            dispatcher.dispatch(task); // push task to worker queue
            task.updateStatus("QUEUED");
            taskService.updateTask(task); // mark queued
        }
    }
}

