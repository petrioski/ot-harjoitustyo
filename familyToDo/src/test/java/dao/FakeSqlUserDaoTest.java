package dao;

import domain.Database;
import domain.User;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


public class FakeSqlUserDaoTest {
    FakeSqlUserDao userDao;
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    @Before
    public void setUp() throws Exception {
        File createdFolder = tempFolder.newFolder("testaus");
        String path = createdFolder.getPath() + "test33.db";
        Database testDB = new Database("jdbc:sqlite:"+path);
        
        userDao = new FakeSqlUserDao(testDB);
        
        
        
    }
    
    @Test
    public void testUserListing() throws SQLException {
        List<User> users = userDao.findAll();
        
        assertThat(users.size(), is(4));
        assertThat(users.get(0).getName(), is("pete"));
    }
 
    @Test
    public void testFindingOneUser() throws SQLException {
        List<User> users = userDao.findAll();
        Integer id = users.get(0).getId();
        
        assertThat(userDao.findOne(id).getName(), is("pete"));
    }
    
    @Test
    public void testFindingNullUser() throws SQLException {
        
        Integer id = Integer.MAX_VALUE;
        
        assertThat(userDao.findOne(id), is(nullValue()));
    }
}