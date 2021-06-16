package cyb.periodicals.dao;

import cyb.periodicals.dao.exceptions.DAOException;
import cyb.periodicals.dao.utils.ResultSetParser;
import cyb.periodicals.entities.subscription.Subscription;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SubscriptionDAO implements DAO<Subscription> {
    private static final Logger logger = LogManager.getLogger(SubscriptionDAO.class);
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("sql");
    private final Connection connection;

    public SubscriptionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Subscription> findAll() {
        return null;
    }

    @Override
    public Subscription findById(long id) {
        return null;
    }

    @Override
    public Subscription create(Subscription entity) throws DAOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    BUNDLE.getString("subscriptions.create"), Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setLong(2, entity.getPeriodicalId());
            preparedStatement.setBoolean(3, entity.getConfirmed());
            preparedStatement.setDate(4, Date.valueOf(entity.getStartDate()));
            preparedStatement.setDate(5, Date.valueOf(entity.getEndDate()));
            preparedStatement.setString(6, entity.getPeriod().toString());
            preparedStatement.setLong(7, entity.getReceiptId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setId(resultSet.getLong(1));
                    return entity;
                }
            }
            return null;
        } catch (SQLException e) {
            logger.error("Error while creating subscription.", e);
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public boolean update(Subscription entity) throws DAOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    BUNDLE.getString("subscriptions.update"));
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setLong(2, entity.getPeriodicalId());
            preparedStatement.setBoolean(3, entity.getConfirmed());
            preparedStatement.setDate(4, Date.valueOf(entity.getStartDate()));
            preparedStatement.setDate(5, Date.valueOf(entity.getEndDate()));
            preparedStatement.setString(6, entity.getPeriod().toString());
            preparedStatement.setLong(7, entity.getReceiptId());

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Error while creating subscription.", e);
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public boolean delete(long id) throws DAOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    BUNDLE.getString("subscriptions.delete"));
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Error while finding user by username.", e);
            throw new DAOException(e.getMessage());
        }
    }

    public List<Subscription> findByUserIdAndStatus(long userId, boolean confirmed) throws DAOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    BUNDLE.getString("subscriptions.find_by_user_id_and_status"));
            preparedStatement.setLong(1, userId);
            preparedStatement.setBoolean(2, confirmed);
            List<Subscription> result = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(ResultSetParser.getSubscription(resultSet));
            }

            return result;
        } catch (SQLException e) {
            logger.error("Error while finding subscription by user id and status.", e);
            throw new DAOException(e.getMessage());
        }
    }
}
