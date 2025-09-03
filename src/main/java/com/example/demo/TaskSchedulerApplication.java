package com.example.demo;
import com.example.demo.worker.Worker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class TaskSchedulerApplication {

	private final Worker worker;

	public static void main(String[] args) {
		SpringApplication.run(TaskSchedulerApplication.class, args);
	}

}
