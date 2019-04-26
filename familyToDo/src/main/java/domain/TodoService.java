
package domain;

import dao.TodoDao;
import dao.UserDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* Luokka vastaa sovelluksen logiikasta
*
*/
public class TodoService {
    private TodoDao todoDao;
    private UserDao userDao;
    private User currentUser;

    /**
     * Konstruktori alustaa luokan käyttäen tehtävä- 
     * ja käyttjärajapintoja
     * 
     * @param todoDao rajapinta tehtäville
     * @param userDao rajapinta käyttäjille
     */
    public TodoService(TodoDao todoDao, UserDao userDao) {
        this.todoDao = todoDao;
        this.userDao = userDao;
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
     * @throws Exception
     */
    
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
            current = todoDao.findAll().stream()
                    .filter(t -> t.getUser().equals(this.currentUser))
                    .filter(t -> t.isCompleted())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.toString());
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
     * Lisää uuden tehtävän kirjautuneelle käyttäjälle
     * @param task lisättävä tehtävä
     */
    
    public void addNewTask(Todo task) {
        try {
            todoDao.addOne(task);
        } catch (Exception e) {
            System.out.println("error");
        }
    }
    
}