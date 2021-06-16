package cyb.periodicals.services.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class ConnectionPool {
    public static final int MAX_CONNECTIONS = 10;
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("application");
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final Map<Connection, Boolean> connections = new HashMap<>(MAX_CONNECTIONS);
    private static ConnectionPool instance = null;

    static {
        try {
            Class.forName(BUNDLE.getString("database.driver"));
        } catch (ClassNotFoundException e) {
            logger.error("Cannot load DBMS driver class.", e);
        }

        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            connections.put(createConnection(), true);
        }
    }

    private ConnectionPool() {}

    public static ConnectionPool getInstance(){
        if (instance==null)
            instance = new ConnectionPool();
        return instance;
    }

    private static Connection createConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(BUNDLE.getString("database.url"),
                    BUNDLE.getString("database.username"), BUNDLE.getString("database.password"));
        } catch (SQLException e) {
            logger.error("Cannot create connection.", e);
        }

        return connection;
    }

    public synchronized Connection getConnection() {
        for (Map.Entry<Connection, Boolean> current : connections.entrySet()) {
            if (current.getKey() != null && current.getValue()) {
                current.setValue(false);
                return current.getKey();
            }
        }

        logger.error("Cannot get connection. No connections available.");
        return null;
    }

    public synchronized void releaseConnection(Connection connection) {
        if (connections.containsKey(connection)) {
            connections.put(connection, true);
        }
    }
}
