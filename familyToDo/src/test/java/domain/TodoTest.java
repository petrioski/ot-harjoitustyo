/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author karhunko
 */
public class TodoTest {
    User testCase;
    Todo baseCase;
    
    
    
    @Before
    public void setUp() {
        testCase = new User("testPerson", "doWork");        
    }
    
    @Test
    public void createTask() {
        Todo newTodo = new Todo("Do the dishes", testCase);
        assertThat(newTodo.getTask(), is("Do the dishes"));
    }

    @Test
    public void newTaskIsNotDone() {
        Todo newTodo = new Todo("Do the dishes", testCase);
        assertThat(newTodo.isCompleted(), is(false));
    }
    
    @Test
    public void doneTaskReturnsTrue() {
        Todo newTodo = new Todo("Do the dishes", testCase);
        newTodo.setCompleted();
        assertThat(newTodo.isCompleted(), is(true));
    }

}
