
package domain;

import java.sql.*;
import org.sqlite.SQLiteConfig;

/**
 * Luokka vastaa tietokannan luonnista sekä yhteyden luomisesta tietokantaan
 * 
 */
public class Database {
    private String databaseAddress;
    
    /**
     * Luo Database-olion annetulla osoitteella.
     * Luonnin yhteydessä konstruktori varmistaa, että ohjelman käyttämä 
     * tietokanta on olemassa tauluineen ja tarpeen vaatiessa luo ne itse.
     * 
     * @param databaseAddress tietokannan osoite
     * @throws ClassNotFoundException heittää luokka ei löydy poikkeuksen
     */
    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
        
        try {
            this.checkTablesExists();
        } catch (Exception e) {
            
        }
    }
    /**
     * Metodi huolehtii yhteydestä tietokantaan
     * @return yhteys tietokantaan
     * @throws SQLException heittää sql-poikkeuksen
     */
    public Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();  
        config.enforceForeignKeys(true);
        Connection conn = DriverManager.getConnection(databaseAddress
                                        , config.toProperties());
        return conn;
    }
    
    //private methods for creating necessary database tables
    private void checkTablesExists() throws SQLException {
        checkUserTable();
        checkTasksTable();
        checkSettingsTable();
        
    }
    
    private void checkUserTable() throws SQLException {
        String userTable = "CREATE TABLE IF NOT EXISTS Users (\n"
                            + "id integer PRIMARY KEY, \n"
                            + "name text NOT NULL, \n"
                            + "username text NOT NULL UNIQUE, \n"
                            + "password text NOT NULL \n"
                            + ");";
                
        try (Connection conn = this.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute(userTable);
        }
    }
    
    private void checkTasksTable() throws SQLException {
        String taskTable = "CREATE TABLE IF NOT EXISTS Tasks (\n"
                            + "id integer PRIMARY KEY, \n"
                            + "task text NOT NULL, \n"
                            + "dueDate text NOT NULL, \n"
                            + "startDate text, \n"
                            + "doneDate text, \n"
                            + "completed boolean NOT NULL, \n"
                            + "user integer NOT NULL, \n"
                            + "recurringInterval integer, \n"
                            + "FOREIGN KEY (user) REFERENCES Users(id)"
                            + ");";
                
        try (Connection conn = this.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute(taskTable);
        }
    }
    
    private void checkSettingsTable() throws SQLException {
        String prefTable = "CREATE TABLE IF NOT EXISTS Preferences (\n"
                            + "id integer PRIMARY KEY, \n"
                            + "dueDays integer NOT NULL, \n"
                            + "recurringInterval integer NOT NULL, \n"
                            + "user integer NOT NULL, \n"
                            + "FOREIGN KEY (user) REFERENCES Users(id)"
                            + ");";
                
        try (Connection conn = this.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute(prefTable);
        }
    }
}
