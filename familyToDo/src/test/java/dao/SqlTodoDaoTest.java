package dao;

import domain.Database;
import domain.RecurringTodo;
import domain.Todo;
import domain.User;
import domain.UserPreferences;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import org.hamcrest.Matchers;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


public class SqlTodoDaoTest {
    SqlTodoDao todoDao;
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    @Before
    public void setUp() throws Exception {
        File createdFolder = tempFolder.newFolder("testaus2");
        String path = createdFolder.getPath() + "test33.db";
        Database testDB = new Database("jdbc:sqlite:"+path);
        SqlUserDao userDao = new SqlUserDao(testDB);
        todoDao = new SqlTodoDao(testDB);        
        userDao.addOne(new User("name", "username", "password"));
    }
    
    @Test
    public void testTaskListing() throws SQLException {
        List<Todo> todos = todoDao.findAll();
        
        assertThat(todos.size(), is(0));
        
    }
 
    @Test
    public void testFindingOneTask() throws SQLException {
        addTestTodo("imuroi");
        
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
        addRecurringTestTodo("vie roskat");
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
        addTestTodo("osta ruokaa");
        List<Todo> todos = todoDao.findAll();
        Integer id = -1;
        for (Todo x : todos) {
            if (x.getTask().equals("osta ruokaa")) {
                id = x.getTaskId();
            }
        }
        assertThat(id, Matchers.greaterThan(0));
    }
    
    
    @Test
    public void testUpdatingTask() throws SQLException {
        addTestTodo("osta ruokaa");
        List<Todo> todos = todoDao.findAll();
        Integer id = -1;
        Todo task = null;
        for (Todo x : todos) {
            if (x.getTask().equals("osta ruokaa")) {
                id = x.getTaskId();
                task = x;
            }
        }
        assertThat(id, Matchers.greaterThan(0));
        
        task.toggleCompleted();
        todoDao.saveOrUpdate(task);
        Todo updated = todoDao.findOne(id);
        
        assertThat(!Objects.equals(task.isCompleted(), updated.isCompleted()), is(false));
    }
    
    
    @Test
    public void testUpdatingRecurringTask() throws SQLException {
        addRecurringTestTodo("osta ruokaa");
        List<Todo> todos = todoDao.findAll();
        Integer id = -1;
        RecurringTodo task = null;
        for (Todo x : todos) {
            if (x.getTask().equals("osta ruokaa")) {
                id = x.getTaskId();
                task = (RecurringTodo) x;
            }
        }
        assertThat(id, Matchers.greaterThan(0));
        
        task.toggleCompleted();
        todoDao.saveOrUpdate(task);
        Todo updated = todoDao.findOne(id);
        
        assertThat(!Objects.equals(task.isCompleted(), updated.isCompleted()), is(false));
    }
    
    private void addTestTodo(String taskname) throws SQLException {        
        Todo task = new Todo(taskname, 1, new UserPreferences(1)); 
        todoDao.saveOrUpdate(task);
    }
    
    private void addRecurringTestTodo(String taskname) throws SQLException {        
        RecurringTodo task = new RecurringTodo(taskname, 1, new UserPreferences(1)); 
        todoDao.saveOrUpdate(task);
    }
}