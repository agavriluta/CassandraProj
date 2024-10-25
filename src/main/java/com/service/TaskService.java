package com.service;

import com.CassandraConnection;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.util.UUID;

public class TaskService {

    public void addTask(UUID taskId, UUID userId, String title, String description, String status) {
        String query = "INSERT INTO tasks (task_id, user_id, title, description, status, created_at) VALUES (?, ?, ?, ?, ?, toTimestamp(now()))";
        try (CqlSession session = CassandraConnection.getSession()) {
            session.execute(session.prepare(query).bind(taskId, userId, title, description, status));
            System.out.println("Task added successfully");
        }
    }

    public void getTasksByUser(UUID userId) {
        String query = "SELECT * FROM tasks WHERE user_id = ?";
        try (CqlSession session = CassandraConnection.getSession()) {
            ResultSet resultSet = session.execute(session.prepare(query).bind(userId));
            for (Row row : resultSet) {
                System.out.println("Task ID: " + row.getUuid("task_id"));
                System.out.println("Title: " + row.getString("title"));
                System.out.println("Description: " + row.getString("description"));
                System.out.println("Status: " + row.getString("status"));
                System.out.println("Created At: " + row.getInstant("created_at"));
                System.out.println();
            }
        }
    }

    public void getTaskById(UUID taskId, UUID userId) {
        String query = "SELECT * FROM tasks WHERE user_id = ? AND task_id = ?";
        try (CqlSession session = CassandraConnection.getSession()) {
            Row row = session.execute(session.prepare(query).bind(userId, taskId)).one();
            if (row != null) {
                System.out.println("Task ID: " + row.getUuid("task_id"));
                System.out.println("Title: " + row.getString("title"));
                System.out.println("Description: " + row.getString("description"));
                System.out.println("Status: " + row.getString("status"));
                System.out.println("Created At: " + row.getInstant("created_at"));
            } else {
                System.out.println("Task not found");
            }
        }
    }

    public void updateTaskStatus(UUID taskId, String newStatus, UUID userId) {
        String query = "UPDATE tasks SET status = ? WHERE user_id = ? AND task_id = ?";
        try (CqlSession session = CassandraConnection.getSession()) {
            session.execute(session.prepare(query).bind(newStatus, userId, taskId));
            System.out.println("Task status updated successfully");
        }
    }

    public void deleteTask(UUID userId, UUID taskId) {
        String query = "DELETE FROM tasks WHERE user_id = ? AND task_id = ?";
        try (CqlSession session = CassandraConnection.getSession()) {
            session.execute(session.prepare(query).bind(userId, taskId));
            System.out.println("Task deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
