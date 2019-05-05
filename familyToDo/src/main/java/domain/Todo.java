
package domain;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;


/**
 * Luokka kuvaa normaalia tehtävää, joka ei toistu automaattisesti
 * 
 */

public class Todo implements Comparable<Todo> {    
    private Integer taskId;
    private String task;
    private LocalDate dueDate;
    private LocalDate startDate;
    private LocalDate doneDate;
    private Boolean completed;    
    private Integer userId;
    private Integer defaultDuration;
    
    /**
     * Luo uuden tehtävän nimen ja käyttäjän perusteella. 
     * Tehtävän määräpäivä tulee kirjautuneen käyttäjän oletusasetuksista.
     * Tehtävän aloituspäivä on luontipäivä.
     * Uusi tehtävä on oletusarvoisesti tekemätön
     * 
     * @param task tehtävän nimi
     * @param user käyttäjän id
     * @param settings käyttäjän oletusasetukset tehtävien kestolle
     */
    
    public Todo(String task, int user, UserPreferences settings) {
        this.task = task;        
        this.userId = user;
        this.completed = false;
        this.defaultDuration = settings.getDefaultDuration();
        this.dueDate = LocalDate.now().plusDays(this.defaultDuration);
        this.startDate = LocalDate.now();
    }
    
    /**
     * Luo uuden tehtävän kaikilla parametreilla. 
     * Jos tehtävä merkitään tehdyksi ja uudelleen tekemättömäksi, niin 
     * uusi määräpäivä määräytyy alkuperäisen aloituspäivän ja määräpäivän 
     * erotuksen perusteella.
     * 
     * @param id tehtävän id
     * @param task tehtävän nimi
     * @param user käyttäjän id
     * @param dueDate tehtävän määräpäivä
     * @param startDate tehtävän aloituspäivä
     * @param doneDate päivä, jolloin tehtävä merkitty tehdyksi
     * @param completed tehtävän status, true jos tehty
     */
    public Todo(int id, String task, int user, 
                LocalDate dueDate, LocalDate startDate,
                LocalDate doneDate, Boolean completed
                ) {
        this.taskId = id;
        this.task = task;        
        this.userId = user;
        this.completed = completed;        
        this.dueDate = dueDate;
        this.startDate = startDate;
        this.doneDate = doneDate;     
        int duration = (int) DAYS.between(startDate, dueDate);
        if (duration < 0) {
            duration = 0;
        }
        this.defaultDuration = duration;
    }
    
    
    
    public Integer getDefaultDuration() {
        return defaultDuration;
    }

    public void setDefaultDuration(Integer defaultDuration) {
        this.defaultDuration = defaultDuration;
    }
    
    public String getTask() {
        return task;
    }

    
    public void changeTaskName(String task) {
        this.task = task;
    }

    public LocalDate getEndDate() {
        return dueDate;
    }

    public void changeDueDate(LocalDate endDate) {
        this.dueDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Boolean isCompleted() {
        return completed;
    }

    
    public void setCompleted() {
        this.completed = true;
        this.doneDate = LocalDate.now();
    }

    public Integer getUser() {
        return userId;
    }

    public void setUser(int user) {
        this.userId = user;
    }
    
    /**
     * Vertailee kahta eri tehtävää.
     * Ensimmäiseksi vertailussa tulevat tekemättömät tehtävät. Jos tehtävillä
     * sama tilan totuusarvo, completed, niin tehtävät järjestyvät 
     * päivämäärän mukaan ja lopuksi nimen mukaan aakkosjärjestyksessä.
     * 
     * @param next vertailtava tehtävä
     * @return -1 jos tulee ennen vertailtavaa taskia, 1 jos jälkeen, muuten 0
     */
    @Override
    public int compareTo(Todo next) {
        if (this.isCompleted() && !next.isCompleted()) {
            return 1;
        } else if (!this.isCompleted() && next.isCompleted()) {
            return -1;
        // done tasks
        } else if (this.isCompleted() && next.isCompleted()) {             
            
            return compareSameCompletionStatuses(next);
            
        } else { // both undone
            if (this.dueDate.equals(next.dueDate)) {
                // finally by name
                return this.task.compareTo(next.task);
            }
            return this.dueDate.compareTo(next.dueDate);
        }        
    }
    
    private int compareSameCompletionStatuses(Todo next) {
        // first by done date
        if (this.doneDate.equals(next.doneDate)) {
            // then by due date
            if (this.dueDate.equals(next.dueDate)) {
                // finally by name
                return this.task.compareTo(next.task);
            }
            return -1 * dueDate.compareTo(next.dueDate);
        } else {                
            return -1 * doneDate.compareTo(next.doneDate);
        }  
    }
    
    
    public Integer getTaskId() {
        return this.taskId;
    }
    
    
    public LocalDate getDoneDate() {
        return doneDate;
    }

    
    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    
    public Boolean getCompleted() {
        return completed;
    }

    
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
    /**
     * Metodi kääntää totuusarvon tehtävälle.
     * Tehdystä tulee tekemätön ja tekemättömästä tehty. 
     * Samalla päivitetään eräpäivät tehtävän luonnin yhteydessä valittuun 
     * määräpäiviin.
     * 
     */
    public void toggleCompleted() {
        if (!isCompleted()) {
            setCompleted();
        } else {
            this.completed = false;
            this.doneDate = null;
            changeDueDate(LocalDate.now().plusDays(this.defaultDuration));
        }
        
    }


    
    
    
    
}
