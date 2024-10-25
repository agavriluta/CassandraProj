package com;

import com.service.TaskService;
import com.service.UserService;

import java.util.UUID;

public class App {
    public static void main(String[] args) {

        UserService userService = new UserService();
        TaskService taskService = new TaskService();

        try {
            UUID userId = UUID.randomUUID();
            String username = "john_doe";
            String email = "john@example.com";
            System.out.println("=== Creating a new user ===");
            userService.addUser(userId, username, email);

            System.out.println("\n=== Retrieving the created user ===");
            userService.getUserById(userId);

            UUID taskId = UUID.randomUUID();
            String title = "Complete project report";
            String description = "Complete the project report by the end of the week.";
            String status = "in progress";
            System.out.println("\n=== Creating a new task for the user ===");
            taskService.addTask(taskId, userId, title, description, status);

            System.out.println("\n=== Retrieving all tasks for the user ===");
            taskService.getTasksByUser(userId);

            System.out.println("\n=== Retrieving a specific task by task ID ===");
            taskService.getTaskById(taskId, userId);

            String newStatus = "completed";
            System.out.println("\n=== Updating the status of the task ===");
            taskService.updateTaskStatus(taskId, newStatus, userId);

            System.out.println("\n=== Retrieving the updated task ===");
            taskService.getTaskById(taskId, userId);

            System.out.println("\n=== Deleting the task by task ID ===");
            taskService.deleteTask(userId, taskId);

            System.out.println("\n=== Trying to retrieve the deleted task ===");
            taskService.getTaskById(taskId, userId);

            System.out.println("\n=== Deleting the user ===");
            userService.deleteUser(userId);

            System.out.println("\n=== Trying to retrieve the deleted user ===");
            userService.getUserById(userId);

        } finally {
            CassandraConnection.close();
        }
    }
}
