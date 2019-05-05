
package dao;

import domain.*;
import java.sql.*;
import java.util.List;


public class SqlUserPreferencesDao implements PreferencesDao {
    private Database database;

    public SqlUserPreferencesDao(Database database) throws Exception {
        this.database = database;
    }
    
    
    @Override
    public UserPreferences findOne(Integer userId) throws SQLException {
        String query = "SELECT * FROM Preferences WHERE user = ?";
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet result = stmt.executeQuery();
            
            if (!result.next()) {
                return null;
            } else {
                UserPreferences pref = new UserPreferences(
                                    result.getInt("id"),
                                    result.getInt("dueDays"),
                                    result.getInt("recurringInterval"));
            
                return pref;
            }
        }         
    }

    

    @Override
    public UserPreferences saveOrUpdate(UserPreferences object) throws Exception {
        if (this.findOne(object.getUserId()) == null) {
            this.addOne(object);
        } else {
            this.update(object);
        }
        return null;
    }

    

    @Override
    public void addOne(UserPreferences pref) throws SQLException {
        String insertSQL = "INSERT INTO Preferences \n"
                + "(dueDays, recurringInterval, user) \n"
                + "VALUES (?, ?, ?)";
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(insertSQL);            
            stmt.setInt(1, pref.getDefaultDuration());
            stmt.setInt(2, pref.getRecurringInterval());
            stmt.setInt(3, pref.getUserId());
            stmt.executeUpdate();
        }
    }
    
    public void update(UserPreferences pref) throws SQLException {
        String insertSQL = "UPDATE Preferences \n"
                + "SET dueDays = ?, \n"
                    + "recurringInterval = ? \n"                    
                + "WHERE  user = ?";
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(insertSQL);            
            stmt.setInt(1, pref.getDefaultDuration());
            stmt.setInt(2, pref.getRecurringInterval());
            stmt.setInt(3, pref.getUserId());
            stmt.executeUpdate();
            
        }
        
    }
    
    @Override
    public void delete(Integer key) throws Exception {
        //Kept due to interface compliance and future expansions
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public List<UserPreferences> findAll() throws SQLException {
        //Kept due to interface compliance and future expansions
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
