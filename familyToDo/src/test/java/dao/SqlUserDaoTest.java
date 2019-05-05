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


public class SqlUserDaoTest {
    SqlUserDao userDao;
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    @Before
    public void setUp() throws Exception {
        File createdFolder = tempFolder.newFolder("testaus1");
        String path = createdFolder.getPath() + "test33.db";
        Database testDB = new Database("jdbc:sqlite:"+path);
        
        userDao = new SqlUserDao(testDB);
        
        
        
    }
    
    @Test
    public void testUserListingEmpty() throws SQLException {
        List<User> users = userDao.findAll();
        
        assertThat(users.size(), is(0));
        
    }
 
    @Test
    public void testUserListing() throws SQLException {
        User testUser = new User("name", "username", "password");
        userDao.addOne(testUser);
        List<User> users = userDao.findAll();
        
        assertThat(users.size(), is(1));
        
    }
    
    
    @Test
    public void testFindingOneUser() throws SQLException {
        User testUser = new User("name", "username", "password");
        userDao.addOne(testUser);
        List<User> users = userDao.findAll();
        
        Integer id = users.get(0).getId();
        
        assertThat(userDao.findOne(id).getName(), is("name"));
    }
    
    @Test
    public void testFindingNullUser() throws SQLException {        
        Integer id = Integer.MAX_VALUE;
        
        assertThat(userDao.findOne(id), is(nullValue()));
    }
    
    
    @Test
    public void testFindingUserByName() throws SQLException {
        assertThat(userDao.findByName("") == null, is(true));
        User testUser = new User("name", "username", "password");
        userDao.addOne(testUser);
        assertThat(userDao.findByName("username").getId(), is(1));
    }
}