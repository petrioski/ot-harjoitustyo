package dao;

import domain.Database;
import domain.Todo;
import domain.UserPreferences;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import org.hamcrest.Matchers;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


public class FakeSqlTodoDaoTest {
    FakeSqlTodoDao todoDao;
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    @Before
    public void setUp() throws Exception {
        File createdFolder = tempFolder.newFolder("testaus");
        String path = createdFolder.getPath() + "test33.db";
        Database testDB = new Database("jdbc:sqlite:"+path);
        FakeSqlUserDao userDao = new FakeSqlUserDao(testDB);
        todoDao = new FakeSqlTodoDao(testDB);        
    }
    
    @Test
    public void testTaskListing() throws SQLException {
        List<Todo> todos = todoDao.findAll();
        
        assertThat(todos.size(), is(9));
        assertThat(todos.get(0).getTask(), is("imuroi"));
    }
 
    @Test
    public void testFindingOneTask() throws SQLException {
        List<Todo> todos = todoDao.findAll();
        Integer id = todos.get(0).getTaskId();
        
        assertThat(todoDao.findOne(id).getTask(), is("imuroi"));
    }
    
    @Test
    public void testFindingNullTask() throws SQLException {        
        Integer id = Integer.MAX_VALUE;
        
        assertThat(todoDao.findOne(id), is(nullValue()));
    }
    
    @Test
    public void testFindingRecurringTask() throws SQLException {
        List<Todo> todos = todoDao.findAll();
        Integer id = -1;
        for (Todo x : todos) {
            if (x.getTask().equals("vie roskat")) {
                id = x.getTaskId();
            }
        }
        
        assertThat(todoDao.findOne(id) != null, is(true));
    }
    
    @Test
    public void testAddingTask() throws SQLException {
        Todo task = new Todo("osta ruokaa", 1, new UserPreferences(1));
        todoDao.saveOrUpdate(task);
        List<Todo> todos = todoDao.findAll();
        Integer id = -1;
        for (Todo x : todos) {
            if (x.getTask().equals("osta ruokaa")) {
                id = x.getTaskId();
            }
        }
        assertThat(id, Matchers.greaterThan(0));
    }
}