
package domain;

import dao.TodoDao;
import dao.UserDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TodoService {
    private TodoDao todoDao;
    private UserDao userDao;
    private User currentUser;

    
    public TodoService(TodoDao todoDao, UserDao userDao) {
        this.todoDao = todoDao;
        this.userDao = userDao;
        this.currentUser = null;
    }
    
    public boolean login(String username, String pass) throws Exception {
        User attemptingUser = userDao.findByName(username);
        if (attemptingUser == null) {
            return false;
        } else if (attemptingUser.getPassword().equals(pass)) {
            currentUser = attemptingUser;
            return true;
        }
        return false;
    }
    
    public List<Todo> getOpenAndRecentlyDone() {
        if (currentUser == null) {
            return new ArrayList<>();
        }
        List<Todo> current = new ArrayList<>();
        
        try {
            current = todoDao.findAll().stream()
                    .filter(t -> t.getUser().equals(this.currentUser))
                    .filter(t -> (!t.isCompleted() 
                                  || t.getDoneDate().equals(LocalDate.now()))
                                  )
                    .collect(Collectors.toList());
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return current;
    }
    
    
    public List<Todo> getDoneTasks() {
        if (currentUser == null) {
            return new ArrayList<>();
        }
        List<Todo> current = new ArrayList<>();
        
        try {
            current = todoDao.findAll().stream()
                    .filter(t -> t.getUser().equals(this.currentUser))
                    .filter(t -> t.isCompleted())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return current;
    }
    
    
    
    
    public User getCurrentUser() {
        return this.currentUser;
    }
    
    public void addNewTask(Todo task) {
        try {
            todoDao.addOne(task);
        } catch (Exception e) {
            System.out.println("error");
        }
    }
    
}