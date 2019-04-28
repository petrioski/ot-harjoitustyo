/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import domain.Todo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author karhunko
 */
public class SqlTodoDao implements TodoDao {
    private List<Todo> tasks;
    
                
    public SqlTodoDao() {
        this.tasks = new ArrayList<>();        
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
    public List<Todo> findAll()  {
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
