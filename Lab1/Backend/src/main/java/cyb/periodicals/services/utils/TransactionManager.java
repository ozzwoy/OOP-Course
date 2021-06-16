package cyb.periodicals.services.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class TransactionManager {
    private static final Logger logger = LogManager.getLogger(TransactionManager.class);

    public static void beginTransaction(Connection connection) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Error while creating transaction.", e);
        }
    }

    public static void commit(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("Error while committing transaction.", e);
        }
    }

    public static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("Error while transaction rollback.", e);
        }
    }

    public static void finishTransaction(Connection connection) {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("Error while finishing transaction.", e);
        }
    }
}
