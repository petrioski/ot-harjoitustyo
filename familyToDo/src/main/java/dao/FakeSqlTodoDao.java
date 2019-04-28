/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.RecurringTodo;
import domain.Todo;
import domain.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.layout.VBox;

/**
 *
 * @author karhunko
 */
public class FakeSqlTodoDao implements TodoDao {
    private List<Todo> tasks;
    
                
    public FakeSqlTodoDao() {
        this.tasks = new ArrayList<>();
        User loggedIn;
        loggedIn = new User("pete", "petri-man", "pass10");
        Todo pyykit = new Todo("pese pyykit", loggedIn);
        pyykit.changeDueDate(LocalDate.of(2019, 04, 16));
        Todo homework = new Todo("tee läksyt", loggedIn);
        homework.changeDueDate(LocalDate.parse("2019-04-17"));
        homework.toggleCompleted();
        RecurringTodo petaus = new RecurringTodo("petaa sänky", loggedIn, 1);
        this.tasks.add(new Todo("imuroi", loggedIn));
        this.tasks.add(new Todo("tee tiskit", loggedIn));
        this.tasks.add(new Todo("pyyhi pölyt", loggedIn));
        this.tasks.add(homework);
        this.tasks.add(pyykit); 
        this.tasks.add(petaus);
        
        Todo oldTask = new Todo("makaronilaatikko", new User("test", "testUser", "passTest"));
        oldTask.toggleCompleted();
        oldTask.setDoneDate(LocalDate.now().minusDays(2));
        this.tasks.add(oldTask);
        
        Todo oldTask2 = new Todo("makaronilaatikko2", new User("test2", "testUser2", "passTest"));
        oldTask2.toggleCompleted();
        this.tasks.add(oldTask2);
        
        
    }
    
    
    
    @Override
    public void setCompleted() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeTaskName(String task) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeDueDate(LocalDate date) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Todo findOne(Integer key) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Todo> findAll() {
        return this.tasks;
    }
    
    @Override
    public List<Todo> findDone() throws Exception {
        return this.tasks;
    }
    
    @Override
    public void addOne(Todo task) {
        this.tasks.add(task);
    }
    
    @Override
    public Todo saveOrUpdate(Todo object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
