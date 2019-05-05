
package domain;

import dao.TodoDao;
import dao.UserDao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import dao.PreferencesDao;

/**
* Luokka vastaa sovelluksen logiikasta
*
*/
public class TodoService {
    private TodoDao todoDao;
    private UserDao userDao;
    private PreferencesDao settingsDao;
    private User currentUser;
    private UserPreferences settings;
    
    

    /**
     * Konstruktori alustaa luokan käyttäen tehtävä- 
     * ja käyttjärajapintoja
     * 
     * @param todoDao rajapinta tehtäville
     * @param userDao rajapinta käyttäjille
     * @param settingsDao rajapinta oletusasetuksille
     */
    public TodoService(TodoDao todoDao
                       , UserDao userDao
                       , PreferencesDao settingsDao) {
        this.todoDao = todoDao;
        this.userDao = userDao;
        this.settingsDao = settingsDao;
        this.currentUser = null;
    }
    
    /**
     * Metodin avulla kirjaudutaan sisään sovellukseen. 
     * Metodi tarkistaa löytyykö käyttäjää tiedoista ja täsmääkö 
     * salasana 
     * 
     * @param username käyttäjänimi
     * @param pass salasana
     * @return palauttaa true, jos salasana ja käyttäjänimi täsmää, 
     *          muuten false
     * @throws Exception heittää poikkeuksen
     */
    
    public boolean login(String username, String pass) throws Exception {
        User attemptingUser = userDao.findByName(username);
        
        if (attemptingUser == null) {
            return false;
        } else if (attemptingUser.getPassword().equals(pass)) {
            currentUser = attemptingUser;
            this.refreshSettings();
            return true;
        }
        
        return false;
    }
    /**
     * Metodi kirjaa käyttäjän ulos ja tyhjentää oletusasetukset
     */
    public void logOut() {
        this.currentUser = null;
        this.settings = null;
    }
    
    private void refreshSettings() {
        int userId = this.getCurrentId();
        UserPreferences defaults = null;
        try {
            defaults = settingsDao.findOne(userId);
        } catch (Exception e) {
            
        }
        
        if (defaults != null) {
            this.settings = defaults;
        } else {
            this.settings = new UserPreferences(userId);
        }
            
    }
    
    /**
     * Hakee avoimet tehtävät sekä samana päivänä suljetut
     * @return palauttaa listan Todo-olioita, jotka kuuluvat
     * kirjautuneelle käyttäjälle
     */
    public List<Todo> getOpenAndRecentlyDone() {
        if (currentUser == null) {
            return new ArrayList<>();
        }
        List<Todo> current = new ArrayList<>();
        
        try {
            current = todoDao.findAll().stream()
                    .filter(t -> t.getUser().equals(this.currentUser.getId()))
                    .filter(t -> (!t.isCompleted() 
                                  || t.getDoneDate().equals(LocalDate.now()))
                                  )
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            
        }
            
        
        return current;
    }
    
    
    /**
     * Hakee tehdyiksi merkityt tehtävät
     * @return palauttaa listan Todo-olioita, jotka kuuluvat
     * kirjautuneelle käyttäjälle
     */
    public List<Todo> getDoneTasks() {
        if (currentUser == null) {
            return new ArrayList<>();
        }
        List<Todo> current = new ArrayList<>();
        
        try {
            current = todoDao.findDone(this.currentUser.getId());
        } catch (Exception e) {
            
        }
        
        return current;
    }
    
    
    /**
     * Metodi palauttaa kirjautuneen käyttäjän
     * @return palauttaa User-olion tai null
     */
  
    public User getCurrentUser() {
        return this.currentUser;
    }
    /**
     * Palauttaa kirjautuneen käyttäjän id:n
     * @return käyttäjän id
     */
    public Integer getCurrentId() {
        return this.currentUser.getId();
    }
    /**
     * Palauttaa sisäänkirjautuneen käyttäjän oletusasetukset
     * @return UserPreferences
     */
    public UserPreferences getCurrentSettings() {
        return this.settings;
    }
    
    /**
     * Palauttaa sisäänkirjautuneen käyttäjän tehtävien oletus määräajan.
     * Kertoo kuinka monen päivän päähän deadline asetetaan oletuksena.
     * @return int oletusmääräaika
     */
    public Integer getDefaultDuration() {
        return this.settings.getDefaultDuration();
    }
    
    /**
     * Palauttaa sisäänkirjautuneen käyttäjän toistuvien tehtävien 
     * oletus uusiutumisfrekvenssin.
     * Kertoo kuinka monen päivän päähän uusi deadline asetetaan oletuksena.
     * @return int oletusuusiutumisfrekvenssi
     */
    public Integer getRepeatInterval() {
        return this.settings.getRecurringInterval();
    }
    
    /**
     * Lisää uuden tehtävän kirjautuneelle käyttäjälle
     * @param task lisättävä tehtävä
     */
    
    public void addNewTask(Todo task) {
        try {
            todoDao.addOne(task);
        } catch (Exception e) {
            
        }
    }
    /**
     * Lisää uuden käyttäjän tietokantaan
     * @param user käyttäjä-olio
     * @return true, jos lisäys onnistui
     */
    public boolean addNewUser(User user) {
        try {
            userDao.addOne(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * lisää uuden tai päivittää vanhan tehtävän tietokantaan
     * @param task päivitettävä tehtävä
     */
        
    public void saveOrUpdateTask(Todo task) {
        try {
            todoDao.saveOrUpdate(task);
        } catch (Exception e) {
            
        }
    }
    
    /**
     * Lisää uudet oletusasetukset tai päivittää vanhat tietokantaan
     * @param pref päivitettävä oletusasetukset
     */
    public void saveOrUpdatePrefences(UserPreferences pref) {
        try {
            settingsDao.saveOrUpdate(pref);            
        } catch (Exception e) {
            
        }
        this.refreshSettings();
    }
    
    /**
     * Poistaa tehtävän tietokannasta
     * @param task päivitettävä tehtävä
     */
    public void deleteTask(Todo task) {
        try {
            todoDao.delete(task.getTaskId());
        } catch (Exception e) {
            
        }
    }
    
    /**
     * Kertoo löytyykö käyttäjänimi jo tietokannasta
     * @param username käyttäjänimi
     * @return Boolean true, jos käyttäjänimi on jo olemassa
     */
    public Boolean userExists(String username) {
        User found = null;
        try {
            found = userDao.findByName(username);
        } catch (Exception e) {
            
        }
        
        if (found != null) {
            return true;
        } 
        
        return false;
    }
    
    /**
     * Testaa täyttääkö uuden käyttäjän syöttämät tiedot asetetut vaatimukset 
     * @param realName käyttjän kutsumanimi
     * @param username käyttäjänimi sisäänkirjautumiseen
     * @param pass1 salasana
     * @param pass2 salasana toiseenkertaan
     * @return palauttaa true, jos onnistui, muuten false
     */
    public Boolean validUserCreation(String realName, String username,
                                    String pass1, String pass2) {
        
        String name = realName.trim();
        String user = username.trim();
        String pass = pass1.trim();
        String checkPass = pass2.trim();
        
        if (user.length() <= 2 || name.length() <= 2) {
            return false;                            
        } else if (!pass.equals(checkPass)
                   || pass.length() <= 2) {                
            return false;
        }
         
        if (this.userExists(user)) {    
            return false;
        }
        
        this.addNewUser(new User(name, user, pass));
 
        return true;
            
    }
}