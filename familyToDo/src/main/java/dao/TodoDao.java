package dao;

import domain.Todo;
import java.sql.SQLException;
import java.util.List;


public interface TodoDao extends  Dao<Todo, Integer> {
        
    List<Todo> findDone(Integer user) throws SQLException;
    
}

