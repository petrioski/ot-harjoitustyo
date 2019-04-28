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
    
}