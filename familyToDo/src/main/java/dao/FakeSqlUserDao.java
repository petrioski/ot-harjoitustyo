
package dao;

import domain.Database;
import domain.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class FakeSqlUserDao implements UserDao {
    private Database database;
    
    
    public FakeSqlUserDao(Database database) throws Exception {
        this.database = database;
        this.addTestUser(new User("pete", "petri-man", "pass10"));
        this.addTestUser(new User("etep", "petri-man99", "pass99"));
        this.addTestUser(new User("test2", "testUser2", "passTest"));
        this.addTestUser(new User("test", "testUser", "passTest"));
        
    }
    
    
    @Override
    public void addOne(User user) throws SQLException {
        String insertSQL = "INSERT INTO Users \n"
                + "(name, username, password) \n"
                + "VALUES (?, ?, ?)";
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(insertSQL);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();
        }
        
    }
    
    @Override
    public User findByName(String user) throws SQLException {
        String searchUser = "SELECT * FROM Users WHERE username = ?";
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(searchUser);
            stmt.setString(1, user);
            
            ResultSet res = stmt.executeQuery();
            
            if (res.next()) {
                User received = new User(res.getInt("id"), 
                                    res.getString("name"),
                                    res.getString("username"),
                                    res.getString("password"));
                return received;
            }
        }
        return null;
    }


    @Override
    public User findOne(Integer id) throws SQLException {
        String searchUser = "SELECT * FROM Users WHERE id = ?";
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(searchUser);
            stmt.setInt(1, id);
            
            ResultSet res = stmt.executeQuery();
            
            if (res.next()) {
                User received = new User(res.getInt("id"), 
                                    res.getString("name"),
                                    res.getString("username"),
                                    res.getString("password"));
                return received;
            }
        }
        
        return null;
    }

    @Override
    public List<User> findAll()  throws SQLException {
        List<User> all = new ArrayList<>();
        String searchUser = "SELECT * FROM Users";
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(searchUser);
            
            
            ResultSet res = stmt.executeQuery();
            
            while (res.next()) {
                User received = new User(res.getInt("id"), 
                                    res.getString("name"),
                                    res.getString("username"),
                                    res.getString("password"));
                all.add(received);
            }
        }
        
        return all;
    }

    @Override
    public User saveOrUpdate(User object) throws Exception {
        //Kept due to interface compliance and future expansions
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void delete(Integer id) throws Exception {
        //Kept due to interface compliance and future expansions
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
    private void addTestUser(User user) throws SQLException {
        this.addOne(user);
    }
}
