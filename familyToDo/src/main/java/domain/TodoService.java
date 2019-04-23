
package domain;

import dao.TodoDao;
import dao.UserDao;
import java.util.ArrayList;
import java.util.List;

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
        
        return null;
    }
    
    public User getCurrentUser() {
        return this.currentUser;
    }
    
    
}