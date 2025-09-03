package com.example.demo.worker;

import com.example.demo.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
@Component
@RequiredArgsConstructor
public class Worker implements CommandLineRunner {

    private final StringRedisTemplate redisTemplate;
    private final TaskHandlers taskHandlers;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private static final String QUEUE_KEY = "task_queue";

    @Override
    public void run(String... args) {
        System.out.println("👷 Worker started, waiting for tasks...");

        while (true) {
            try {
                // Blocking pop from Redis (timeout = 0 means block until a task arrives)
                String payload = redisTemplate.opsForList()
                        .leftPop(QUEUE_KEY, java.time.Duration.ofSeconds(0));

                if (payload != null) {
                    Task task = objectMapper.readValue(payload, Task.class);
                    System.out.println("⚡ Worker picked up task: " + task.getId());

                    // Delegate to handlers based on task type
                    taskHandlers.handle(task);
                }

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(2000); // small backoff in case of failure
                } catch (InterruptedException ignored) {}
            }
        }
    }
}
