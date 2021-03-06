package domain;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;



public class RecurringTodoTest {
    User testCase;
    UserPreferences pref;
    RecurringTodo baseCaseTask, modifiedTask;
    
    
    
    @Before
    public void setUp() {
        Integer userId = 2;
        testCase = new User(userId,"testPerson", "doWork", "pass123");    
        pref = new UserPreferences(userId);
        baseCaseTask = new RecurringTodo("task", testCase.getId(), pref);
        modifiedTask = new RecurringTodo("task", testCase.getId(), pref, 10);        
    }
    
    @Test
    public void testDefaultInterval() {
        assertThat(baseCaseTask.getRecurringInterval(), is(7));
    }
    
    @Test
    public void testCustomInterval() {
        assertThat(modifiedTask.getRecurringInterval(), is(10));
    }
    
    
    
    
    @Test
    public void testChangingCustomInterval() {        
        assertThat(modifiedTask.getRecurringInterval(), is(10));
        try {
            modifiedTask.setRecurringInterval(1);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        assertThat(modifiedTask.getRecurringInterval(), is(1));
    }
    
    @Test
    public void testDoneTaskReturnsTrue() {
        modifiedTask.toggleCompleted();
        assertThat(modifiedTask.isCompleted(), is(true));
    }
    
    
    @Test
    public void taskTitleReturnsInterval() {
        assertThat(modifiedTask.getTaskTitle().contains("10"), is(true));
    }
    
    @Test
    public void testTitleForDoneTaskReturnsDate() {
        modifiedTask.toggleCompleted();
        String thisYear = "" + LocalDate.now().getYear();
        assertThat(modifiedTask.getTaskTitle().contains(thisYear), is(true));
    }
    
    @Test
    public void testDoneTaskReturnsTrueForPastTask() {
        modifiedTask.setCompleted();
        modifiedTask.setDoneDate(LocalDate.now().minusDays(2));
        assertThat(modifiedTask.isCompleted(), is(false));
    }
    
    @Test
    public void testNewDueDate() {
        modifiedTask.toggleCompleted();
        LocalDate doneDate = modifiedTask.getDoneDate();
        LocalDate newDueDate = modifiedTask.getEndDate();
        long difference = DAYS.between(doneDate, newDueDate);
        assertThat(difference, is(10L));
    }
}