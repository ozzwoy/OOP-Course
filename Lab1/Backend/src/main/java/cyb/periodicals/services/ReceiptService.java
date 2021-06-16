package cyb.periodicals.services;

import cyb.periodicals.dao.ReceiptDAO;
import cyb.periodicals.dao.exceptions.DAOException;
import cyb.periodicals.entities.receipt.Receipt;
import cyb.periodicals.services.exceptions.ServiceException;
import cyb.periodicals.services.utils.ConnectionPool;

import java.sql.Connection;
import java.util.List;

public class ReceiptService {
    private static ReceiptService instance = null;

    private ReceiptService() {}

    public static ReceiptService getInstance() {
        if (instance == null)
            instance = new ReceiptService();
        return instance;
    }

    public List<Receipt> findAllReceipts() throws ServiceException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ReceiptDAO receiptDAO = new ReceiptDAO(connection);

        try {
            return receiptDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }
}
