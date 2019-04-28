/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author karhunko
 * 
 */
public class FakeSqlUserDao implements UserDao {
    private ArrayList<User> users;
    
    public FakeSqlUserDao() {
        this.users = new ArrayList<>();
        
        this.users.add(new User("pete", "petri-man", "pass10"));
        this.users.add(new User("etep", "petri-man99", "pass99"));
        this.users.add(new User("test2", "testUser2", "passTest"));
        this.users.add(new User("test", "testUser", "passTest"));
    }
    
    @Override
    public void addOne(User user) {
        this.users.add(user);
    }
    
    @Override
    public User findByName(String user) throws Exception {
        for (User u : this.users) {
            if (u.getName().equals(user)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void changeUserName(String task) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changePassword(String password) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User findOne(String key) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> findAll()  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User saveOrUpdate(User object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String key) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}