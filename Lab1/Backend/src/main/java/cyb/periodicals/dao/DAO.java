package cyb.periodicals.dao;

import cyb.periodicals.dao.exceptions.DAOException;

import java.util.List;

public interface DAO<T> {

    List<T> findAll() throws DAOException;

    T findById(long id) throws DAOException;

    T create(T entity) throws DAOException;

    boolean update(T entity) throws DAOException;

    boolean delete(long id) throws DAOException;
}
