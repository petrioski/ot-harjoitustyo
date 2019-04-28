package domain;

import dao.FakeSqlTodoDao;
import dao.FakeSqlUserDao;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class TodoServiceTest {
//    FakeSqlUserDao userDao;
//    FakeSqlTodoDao todoDao;
    TodoService fakeLogic;
    User testUser;
    
    @Before
    public void setUp() {
        FakeSqlUserDao userDao = new FakeSqlUserDao();
        FakeSqlTodoDao todoDao = new FakeSqlTodoDao();                
        fakeLogic = new TodoService(todoDao, userDao);
        testUser = fakeLogic.getCurrentUser();
    }
    
    @Test
    public void testNullUserAtStart() {
        assertThat(fakeLogic.getCurrentUser(), is(nullValue()));
    }
    
    @Test
    public void testMissingUserLogin() {
        Boolean loginResult = false;
        try {
            loginResult = fakeLogic.login("empty", "empty");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        assertThat(fakeLogic.getCurrentUser(), is(nullValue()));
        assertThat(loginResult, is(false));
    }
    
    
    @Test
    public void testValidUserLogin() {
        Boolean loginResult = false;
        try {
            loginResult = fakeLogic.login("pete", "pass10");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        assertThat(fakeLogic.getCurrentUser().getUsername(), is("petri-man"));
        assertThat(loginResult, is(true));
    }
    
    
    @Test
    public void testValidUserWithWrongPasswordLogin() {
        Boolean loginResult = false;
        try {
            loginResult = fakeLogic.login("pete", "pass10000");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        assertThat(fakeLogic.getCurrentUser(), is(nullValue()));
        assertThat(loginResult, is(false));
    }
    
    @Test
    public void fetchOpenTasksWithNoUser() {
        List<Todo> receivedList = new ArrayList<>();
        
        try {
            receivedList = fakeLogic.getOpenAndRecentlyDone();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        assertThat(receivedList.isEmpty(), is(true));
        
    }
    
    
    @Test
    public void fetchDoneTasksWithNoUser() {
        List<Todo> receivedList = new ArrayList<>();
        
        try {
            receivedList = fakeLogic.getDoneTasks();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        assertThat(receivedList.isEmpty(), is(true));
        
    }
    
    @Test
    public void fetchOpenTasksWithLegitUser() {
        List<Todo> receivedList = new ArrayList<>();
        this.logUserIn();
        
        try {
            receivedList = fakeLogic.getOpenAndRecentlyDone();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        assertThat(receivedList.isEmpty(), is(false));
        assertThat(receivedList.size(), is(6));
        
    }
    
    @Test
    public void fetchDoneTasksWithLegitUser() {
        List<Todo> receivedList = new ArrayList<>();
        this.logUserIn();
        
        try {
            receivedList = fakeLogic.getDoneTasks();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        assertThat(receivedList.isEmpty(), is(false));
        assertThat(receivedList.size(), is(1));
        
    }
    
    
    @Test
    public void fetchOpenTasksWithTodayDones() {
        List<Todo> receivedList = new ArrayList<>();
         try {
            fakeLogic.login("test2", "passTest");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        try {
            receivedList = fakeLogic.getOpenAndRecentlyDone();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        assertThat(receivedList.isEmpty(), is(false));
        assertThat(receivedList.size(), is(1));
        
    }
    
    
    @Test
    public void fetchOnlyDoneTasksWithLegitUser() {
        List<Todo> receivedList = new ArrayList<>();
        
        try {
            fakeLogic.login("test", "passTest");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        try {
            receivedList = fakeLogic.getOpenAndRecentlyDone();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        assertThat(receivedList.isEmpty(), is(true));        
        
    }
    
    @Test
    public void addNewTask() {
        logUserIn();
        
        int numberOfOldTasks = fakeLogic.getOpenAndRecentlyDone().size();
        Todo t = new Todo("buy something", fakeLogic.getCurrentUser());
        fakeLogic.addNewTask(t);
                
        
        int numberOfNewTasks = fakeLogic.getOpenAndRecentlyDone().size();
        int countChange = numberOfNewTasks - numberOfOldTasks;
        
        assertThat(countChange, is(1));
    }
    
    private void logUserIn() {
        try {
            fakeLogic.login("pete", "pass10");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}