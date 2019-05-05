package dao;


import java.sql.SQLException;
import java.util.List;


public interface Dao<T, K> {
    T findOne(K key) throws SQLException;
    List<T> findAll() throws SQLException;    
    T saveOrUpdate(T object) throws Exception;
    void delete(K key) throws Exception;
    void addOne(T object) throws SQLException;
}