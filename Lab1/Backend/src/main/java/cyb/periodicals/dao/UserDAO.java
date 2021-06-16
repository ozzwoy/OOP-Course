package cyb.periodicals.dao;

import cyb.periodicals.dao.exceptions.DAOException;
import cyb.periodicals.dao.utils.ResultSetParser;
import cyb.periodicals.entities.user.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class UserDAO implements DAO<User> {
    private static final Logger logger = LogManager.getLogger(UserDAO.class);
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("sql");
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public User create(User entity) throws DAOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(BUNDLE.getString("users.create"),
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getRole().toString());
            preparedStatement.setString(4, entity.getName());
            preparedStatement.setString(5, entity.getAddress());

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
            logger.error("Error while creating user.", e);
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public boolean update(User entity) {
        throw new UnsupportedOperationException("User DAO does not support update operation.");
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException("User DAO does not support delete operation.");
    }

    public User findByUsername(String username) throws DAOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    BUNDLE.getString("users.find_by_username"));
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return ResultSetParser.getUser(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error while finding user by username.", e);
            throw new DAOException(e.getMessage());
        }

        return null;
    }
}
