package dao;


import domain.User;
import java.sql.SQLException;


public interface UserDao extends  Dao<User, Integer> {
    
    User findByName(String username) throws SQLException;
    
    
    
}

