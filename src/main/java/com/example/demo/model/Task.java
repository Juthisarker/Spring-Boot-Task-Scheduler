package com.example.demo.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "type")
    private String type;

    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Column(name = "status")
    private String status;

    @Column(name = "runAt")
    private Instant runAt;

    @Column(name = "attempts")
    private Integer attempts;

    @Column(name = "maxAttempts")
    private Integer maxAttempts;

    // No-arg constructor required by JPA
    public Task() {}

    // All-args constructor
    public Task(UUID id, String type, String payload, String status,
                Instant runAt, Integer attempts, Integer maxAttempts) {
        this.id = id;
        this.type = type;
        this.payload = payload;
        this.status = status;
        this.runAt = runAt;
        this.attempts = attempts;
        this.maxAttempts = maxAttempts;
    }

    @PrePersist
    public void init() {
        if (id == null) id = UUID.randomUUID();
        if (status == null) status = "PENDING";
        if (runAt == null) runAt = Instant.now();
        if (attempts == null) attempts = 0;
        if (maxAttempts == null) maxAttempts = 5;
    }

    // ----------------------
    // Domain methods
    // ----------------------

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public void incrementAttempts() {
        if (attempts == null) attempts = 0;
        this.attempts++;
    }

    public boolean hasReachedMaxAttempts() {
        return attempts != null && maxAttempts != null && attempts >= maxAttempts;
    }

    public void reschedule(Instant newRunAt) {
        this.runAt = newRunAt;
    }

    // ----------------------
    // Getters and Setters
    // ----------------------

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Instant getRunAt() { return runAt; }
    public void setRunAt(Instant runAt) { this.runAt = runAt; }

    public Integer getAttempts() { return attempts; }
    public void setAttempts(Integer attempts) { this.attempts = attempts; }

    public Integer getMaxAttempts() { return maxAttempts; }
    public void setMaxAttempts(Integer maxAttempts) { this.maxAttempts = maxAttempts; }
}

