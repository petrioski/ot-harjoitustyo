package dao;


import java.util.List;


public interface Dao<T, K> {
    T findOne(K key) throws Exception;
    //List<T> findAll() throws Exception;
    List<T> findAll();
    T saveOrUpdate(T object) throws Exception;
    void delete(K key) throws Exception;
    void addOne(T object);
}