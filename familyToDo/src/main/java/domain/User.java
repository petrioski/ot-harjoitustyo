
package domain;

import java.util.Objects;

/**
 * Ohjelman käyttäjäoliosta vastaava luokka
 * 
 */
public class User {
    private String name;
    private String username;
    private String password;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    /**
     * Metodi alustaa käyttäjä-olion uuden luonnin yhteydessä
     * @param name nimi
     * @param username käyttäjänimi
     * @param password salasana
     */
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
    
    public User(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }
    
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    
    @Override
    public String toString() {
        return this.username;
    }
    
    /**
     * Metodi vastaa User-olion vertailusta
     * @param user verrattava olio
     * @return palauttaa true, jos olio on sama, muuten false
     */
    
    @Override
    public boolean equals(Object user) {
        if (this ==  user) {
            return true;
        }
        
        if (!(user instanceof User)) {
            return false;
        }
        
        User comparedUser = (User) user;
        
        if (this.name.equals(comparedUser.name) 
            && this.username.equals(comparedUser.username)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.username);
        return hash;
    }
    
}
