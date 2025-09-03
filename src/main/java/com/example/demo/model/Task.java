package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Data                       // getters, setters, equals, hashCode, toString
@NoArgsConstructor          // JPA requires no-arg constructor
@AllArgsConstructor         // all-args constructor
@Builder                    // builder pattern
public class Task {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "run_at", nullable = false)
    private Instant runAt;

    @Column(name = "attempts", nullable = false)
    private Integer attempts;

    @Column(name = "max_attempts", nullable = false)
    private Integer maxAttempts;

    // Lifecycle hook
    @PrePersist
    public void prePersist() {
        if (status == null) status = "PENDING";
        if (runAt == null) runAt = Instant.now();
        if (attempts == null) attempts = 0;
        if (maxAttempts == null) maxAttempts = 5;
    }

    // Domain methods
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public void incrementAttempts() {
        this.attempts = (attempts == null ? 0 : attempts) + 1;
    }

    public boolean hasReachedMaxAttempts() {
        return attempts != null && maxAttempts != null && attempts >= maxAttempts;
    }

    public void reschedule(Instant newRunAt) {
        this.runAt = newRunAt;
    }
}
