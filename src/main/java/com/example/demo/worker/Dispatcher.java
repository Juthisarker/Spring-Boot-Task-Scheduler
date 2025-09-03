package com.example.demo.worker;

import com.example.demo.model.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
@Component
@RequiredArgsConstructor
public class Dispatcher {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .findAndRegisterModules();

    private static final String QUEUE_KEY = "task_queue";

    /**
     * Send a task to the Redis queue so workers can process it.
     */
    public void dispatch(Task task) {
        try {
            // Convert Task object into JSON
            String payload = objectMapper.writeValueAsString(task);

            // Push it into Redis list (acts like a queue)
            redisTemplate.opsForList().rightPush(QUEUE_KEY, payload);

            System.out.println("✅ Dispatched task to queue: " + task.getId());

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize task for dispatch", e);
        }
    }
}
