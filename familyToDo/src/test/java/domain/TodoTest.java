/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;


import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;


public class TodoTest {
    User testCase;
    Todo baseCase;
    UserPreferences pref;
    
    
    @Before
    public void setUp() {
        testCase = new User(1, "testPerson", "doWork", "pass123");        
        pref = new UserPreferences(1);
    }
    
    @Test
    public void createTask() {
        Todo newTodo = new Todo("Do the dishes", testCase.getId(), pref);
        assertThat(newTodo.getTask(), is("Do the dishes"));
    }

    @Test
    public void newTaskIsNotDone() {
        Todo newTodo = new Todo("Do the dishes", testCase.getId(), pref);
        assertThat(newTodo.isCompleted(), is(false));
    }
    
    @Test
    public void doneTaskReturnsTrue() {
        Todo newTodo = new Todo("Do the dishes", testCase.getId(), pref);
        newTodo.setCompleted();
        assertThat(newTodo.isCompleted(), is(true));
    }
    
    @Test
    public void taskNamechanged() {
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);
        assertThat(newTodo.getTask(), is("Do the dihes"));
        newTodo.changeTaskName("Do the dishes");        
        assertThat(newTodo.getTask(), is("Do the dishes"));
    }
    
    @Test
    public void sortingUndoneTasksSameDue() {        
        
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);
        Todo anotherTask = new Todo("vacuum floors", testCase.getId(), pref);
              
        assertThat(newTodo.compareTo(anotherTask), lessThan(0));
        
    }
    
    @Test
    public void sortingUndoneTasksDifferentDue() {
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);
        
        LocalDate nextDay = newTodo.getEndDate().plusDays(1);
        Todo anotherTask = new Todo("Do the ", testCase.getId(), pref);
        anotherTask.changeDueDate(nextDay);
        assertThat(newTodo.compareTo(anotherTask), lessThan(0));
        
    }
    
    
    @Test
    public void sortingDoneTasksDifferentDoneDate() {
        LocalDate today = LocalDate.now();
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);
        
        LocalDate nextDay = newTodo.getEndDate().plusDays(1);
        Todo anotherTask = new Todo("Do the ", testCase.getId(), pref);
        
        newTodo.toggleCompleted();
        anotherTask.toggleCompleted();
        
        LocalDate yesterday = today.minusDays(1);
        newTodo.setDoneDate(yesterday);
        
        assertThat(newTodo.compareTo(anotherTask), greaterThan(0));
        
    }
    
    @Test
    public void sortingDoneTasksDifferentDue() {
        
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);
        
        LocalDate nextDay = newTodo.getEndDate().plusDays(1);
        Todo anotherTask = new Todo("Do the ", testCase.getId(), pref);
        
        newTodo.toggleCompleted();
        anotherTask.toggleCompleted();
        
        assertThat(newTodo.compareTo(anotherTask), greaterThan(0));
        
    }
    
    @Test
    public void sortingDoneTasksByName() {
        
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);        
        Todo anotherTask = new Todo("Do the waxing of "
                                    + "floors", testCase.getId(), pref);
        
        newTodo.toggleCompleted();
        anotherTask.toggleCompleted();
        
        assertThat(newTodo.compareTo(anotherTask), lessThan(0));
        
    }
    
    @Test
    public void testBothDoneAreDone() {
        
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);        
        Todo anotherTask = new Todo("Do the waxing "
                                    + "of floors", testCase.getId(), pref);
        
        newTodo.toggleCompleted();
        anotherTask.toggleCompleted();
        
        assertThat(newTodo.isCompleted(), is(true));
        assertThat(anotherTask.isCompleted(), is(true));        
        
    }
    
    @Test
    public void testSortingBothAreTheSame() {
        
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);        
        Todo anotherTask = new Todo("Do the dihes", testCase.getId(), pref);
        
        
        assertThat(newTodo.compareTo(anotherTask), is(0));  
        
    }
    
    
    @Test
    public void sortingDoneWithUndone() {
        
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);        
        Todo anotherTask = new Todo("Do the waxing "
                                    + " of floors", testCase.getId(), pref);
        
        newTodo.toggleCompleted();        
        
        assertThat(newTodo.compareTo(anotherTask), greaterThan(0));
        
    }
    
    
    @Test
    public void sortingUndoneWithDone() {
        
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);        
        Todo anotherTask = new Todo("Do the waxing "
                                + " of floors", testCase.getId(), pref);
        
        //newTodo.toggleCompleted();
        anotherTask.toggleCompleted();
        
        assertThat(newTodo.compareTo(anotherTask), lessThan(0));
        
    }
    
    @Test
    public void settingCompleted() {
        
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);        
        
        assertThat(newTodo.isCompleted(), is(false));
        
        newTodo.setCompleted();
        
        assertThat(newTodo.isCompleted(), is(true));
        
        
    }
    
    // test branches for toggling
    @Test
    public void testTogglingUnDone() {
        
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);        
        
        assertThat(newTodo.isCompleted(), is(false));
        
        newTodo.toggleCompleted();
        
        assertThat(newTodo.isCompleted(), is(true));
           
    }
    
    @Test
    public void testTogglingDone() {
        
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);                
        assertThat(newTodo.isCompleted(), is(false));
        
        newTodo.toggleCompleted();        
        assertThat(newTodo.isCompleted(), is(true));
        
        newTodo.toggleCompleted();
        assertThat(newTodo.isCompleted(), is(false));
                
        LocalDate nextDueDate = LocalDate.now().plusDays(2);        
        assertThat(newTodo.getEndDate(), is(nextDueDate));
           
    }
    
    @Test
    public void testTogglingDoneToUndone() {
        
        Todo newTodo = new Todo("Do the dihes", testCase.getId(), pref);                        
        
        newTodo.setCompleted();                
        
        newTodo.toggleCompleted();
        assertThat(newTodo.getCompleted(), is(false));
        
        LocalDate nextDueDate = LocalDate.now().plusDays(2);        
        assertThat(newTodo.getEndDate(), is(nextDueDate));
        assertThat(newTodo.getDoneDate(), is(nullValue()));
        
           
    }
}
