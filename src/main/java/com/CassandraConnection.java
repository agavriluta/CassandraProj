package com;

import com.datastax.oss.driver.api.core.CqlSession;

import java.net.InetSocketAddress;

public class CassandraConnection {
    private static CqlSession session;

    public static CqlSession getSession() {
        if (session == null || session.isClosed()) {
            session = CqlSession.builder()
                    .addContactPoint(new InetSocketAddress("localhost", 9042))
                    .withKeyspace("task_management")
                    .withLocalDatacenter("datacenter1")
                    .build();
        }
        return session;
    }

    public static void close() {
        if (session != null && !session.isClosed()) {
            session.close();
        }
    }
}
