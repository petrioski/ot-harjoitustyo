package dao;


import domain.User;


public interface UserDao extends  Dao<User, String> {
    
    User findByName(String user) throws Exception;
    
    void changeUserName(String task) throws Exception;

    void changePassword(String password) throws Exception;
    
    
}

