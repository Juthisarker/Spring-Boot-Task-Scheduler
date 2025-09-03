package com.example.demo.worker;

import com.example.demo.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskHandlers {

    public void handle(Task task) {
        switch (task.getType()) {
            case "EMAIL" -> handleEmail(task);
            case "REPORT" -> handleReport(task);
            case "NOTIFY" -> handleNotification(task);
            default -> System.out.println("⚠️ Unknown task type: " + task.getType());
        }
    }

    private void handleEmail(Task task) {
        System.out.println("📧 Sending email with payload: " + task.getPayload());
        // TODO: implement actual email sending logic
    }

    private void handleReport(Task task) {
        System.out.println("📊 Generating report with payload: " + task.getPayload());
        // TODO: implement report generation
    }

    private void handleNotification(Task task) {
        System.out.println("🔔 Sending notification with payload: " + task.getPayload());
        // TODO: implement push notification logic
    }
}
