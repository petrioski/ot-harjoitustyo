
package domain;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


public class DatabaseTest {
    Database database;
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    @Before
    public void setUp() throws Exception {
        File createdFolder = tempFolder.newFolder("testaus");
        String path = createdFolder.getPath() + "test99.db";
        database = new Database("jdbc:sqlite:"+path);
    }
    
    @Test
    public void tablesCreated() throws SQLException {
        String sql = "SELECT name \n"
                    + "FROM sqlite_master \n"
                    + "WHERE 1=1 \n"
                    + "AND type ='table' \n"
                    + "AND name NOT LIKE 'sqlite_%' ";
        
        ArrayList<String> tables = new ArrayList<>();
        
        try (Connection conn = database.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            
            while (res.next()) {
                tables.add(res.getString("name"));
            }
        }
     
        assertThat(tables.contains("Users"), is(true));
        assertThat(tables.contains("Tasks"), is(true));
        assertThat(tables.contains("Preferences"), is(true));
        assertThat(tables.size(), is(3));
    }
    
    @Test
    public void foreignKeysAreOn() throws SQLException {
        String sql = "PRAGMA foreign_keys";
        Integer setting = 0;
        
        try (Connection conn = database.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            
            while (res.next()) {
                setting = res.getInt(1);
            }
        }
        
        assertThat(setting, is(1));
    }
}
