package domain;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class TodoServiceTest {
    User testCase;
    List<Todo> tasks;
    
    @Before
    public void setUp() {
        testCase = new User("testPerson", "doWork", "pass123");                
        RecurringTodo baseCaseTask = new RecurringTodo("task", testCase);
        RecurringTodo modifiedTask = new RecurringTodo("task", testCase, 10); 
        tasks = new ArrayList<>();
        tasks.add(baseCaseTask);
        tasks.add(modifiedTask);
    }
    
//    @Test
//    public void testUserLogin() {
//        
//    }
    
}