/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author karhunko
 */
public class User {
    private String name;
    private String username;
    /*add userId */

    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }
    
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String toString() {
        return this.username;
    }
    
}
