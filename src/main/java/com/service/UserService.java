package com.service;

import com.CassandraConnection;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;

import java.util.UUID;

public class UserService {

    public void addUser(UUID userId, String username, String email) {
        String query = "INSERT INTO users (user_id, username, email) VALUES (?, ?, ?)";
        try (CqlSession session = CassandraConnection.getSession()) {
            session.execute(session.prepare(query).bind(userId, username, email));
            System.out.println("User added successfully");
        }
    }

    public void getUserById(UUID userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (CqlSession session = CassandraConnection.getSession()) {
            Row row = session.execute(session.prepare(query).bind(userId)).one();
            if (row != null) {
                System.out.println("User ID: " + row.getUuid("user_id"));
                System.out.println("Username: " + row.getString("username"));
                System.out.println("Email: " + row.getString("email"));
            } else {
                System.out.println("User not found");
            }
        }
    }

    public void updateUserEmail(UUID userId, String newEmail) {
        String query = "UPDATE users SET email = ? WHERE user_id = ?";
        try (CqlSession session = CassandraConnection.getSession()) {
            session.execute(session.prepare(query).bind(newEmail, userId));
            System.out.println("User email updated successfully");
        }
    }

    public void deleteUser(UUID userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        try (CqlSession session = CassandraConnection.getSession()) {
            session.execute(session.prepare(query).bind(userId));
            System.out.println("User deleted successfully");
        }
    }
}
