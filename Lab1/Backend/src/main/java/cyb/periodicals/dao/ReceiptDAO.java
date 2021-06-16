package cyb.periodicals.dao;

import cyb.periodicals.dao.exceptions.DAOException;
import cyb.periodicals.dao.utils.ResultSetParser;
import cyb.periodicals.entities.receipt.Receipt;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReceiptDAO implements DAO<Receipt> {
    private static final Logger logger = LogManager.getLogger(ReceiptDAO.class);
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("sql");
    private final Connection connection;

    public ReceiptDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Receipt> findAll() throws DAOException {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(BUNDLE.getString("receipts.find_all"));
            List<Receipt> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(ResultSetParser.getReceipt(resultSet));
            }

            return result;
        } catch (SQLException e) {
            logger.error("Error while finding all receipts.", e);
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public Receipt findById(long id) {
        return null;
    }

    @Override
    public Receipt create(Receipt entity) throws DAOException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(BUNDLE.getString("receipts.create"),
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, entity.getSum());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(entity.getDoneAt()));

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
    public boolean update(Receipt entity) {
        throw new UnsupportedOperationException("Receipt DAO does not support update operation.");
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException("Receipt DAO does not support delete operation.");
    }
}
