
package domain;

import java.util.Objects;

/**
 * Ohjelman käyttäjää kuvaava luokka
 * 
 */
public class User {
    private String name;
    private String username;
    private String password;
    private Integer id;

    
    /**
     * Metodi alustaa käyttäjä-olion uuden luonnin yhteydessä
     * @param name käyttäjän kutsumanimi
     * @param username käyttäjänimi
     * @param password käyttäjän salasana
     */
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
    
    /**
     * Luo uuden käyttäjän, kun id on jo tiedossa.
     * Tyypillisesti käytetään kun haetaan tietokannasta aiempia käyttäjiä.
     * @param id käyttäjän yksilöivä id
     * @param name käyttäjän kutsumanimi
     * @param username käyttäjänimi
     * @param password käyttäjän salasana
     */
    public User(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
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
    
    
    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }


    public String getUsername() {
        return username;
    }

    
    @Override
    public String toString() {
        return this.username;
    }
    
    public Integer getId() {
        return id;
    }
    
}
