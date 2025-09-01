# Spring-Boot-Task-Scheduler
The project structure will look like this.
task-scheduler/
├── pom.xml
├── docker-compose.yml
├── src/
│   ├── main/
│   │   ├── java/com/example/taskscheduler/
│   │   │   ├── TaskSchedulerApplication.java
│   │   │   ├── config/
│   │   │   │   ├── AmqpConfig.java
│   │   │   │   └── RedisConfig.java
│   │   │   ├── controller/
│   │   │   │   └── TaskController.java
│   │   │   ├── model/
│   │   │   │   └── Task.java
│   │   │   ├── repository/
│   │   │   │   └── TaskRepository.java
│   │   │   ├── service/
│   │   │   │   ├── TaskService.java
│   │   │   │   └── RedisScheduler.java
│   │   │   ├── worker/
│   │   │   │   ├── Dispatcher.java
│   │   │   │   ├── Worker.java
│   │   │   │   └── TaskHandlers.java
│   │   │   └── dto/
│   │   │       ├── CreateTaskRequest.java
│   │   │       └── TaskResponse.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── logback-spring.xml
│   └── test/
│       └── java/com/example/taskscheduler/
│           └── TaskSchedulerApplicationTests.java
