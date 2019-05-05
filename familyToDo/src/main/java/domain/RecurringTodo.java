package domain;


import java.time.LocalDate;

/**
 * Luokka kuvaa toistuvaa tehtävää, joka toistuu määrävälein
 * 
 */

public class RecurringTodo extends Todo  {
    private int recurringInterval;

    /**
     * Luo uuden toistuvaistehtävän oletusarvoilla.
     * 
     * @param task tehtävän nimi
     * @param user käyttäjän id
     * @param settings käyttäjän oletusasetukset
     */
    public RecurringTodo(String task, int user, UserPreferences settings) {
        super(task, user, settings);
        recurringInterval = settings.getRecurringInterval();
    }
    /**
     * Luo uuden toistuvaistehtävän määrätyllä toistumistiheydellä
     * @param task tehtävän nimi
     * @param user käyttäjän id
     * @param settings käyttäjän oletusasetukset
     * @param days toistumistiheys
     */
    public RecurringTodo(String task, int user
                        , UserPreferences settings, int days) {
        super(task, user, settings);
        recurringInterval = days;
    }
    
    public RecurringTodo(int id, String task, int user, LocalDate dueDate, 
                LocalDate startDate, LocalDate doneDate, Boolean completed,
                int days) {
        super(id, task, user, dueDate, startDate, doneDate, completed);
        this.recurringInterval = days;
    }
    
    /**
     * Palauttaa tehtävän nimen toistuvan tehtävän lisätiedoilla.
     * @return String tehtävän nimen, toistumistiheyden ja päivämäärän, 
     * jolloin viimeksi suoritettu
     */
    
    public String getTaskTitle() {
        if (super.getDoneDate() != null) {
            return super.getTask() 
                    + " (" + this.recurringInterval + " pv,"
                    + " edell. " + super.getDoneDate() + ")";
        }
        return super.getTask() + " (" + this.recurringInterval + " pv)";
    }
    
    /**
     * Palauttaa toistuvaistehtävän tilan.
     * 
     * @return Jos tehtävä tehty tänään, niin true, muuten false.
     */
    // Set recurring tasks status to be completed for the same day they 
    // are done and to change status next day to uncompleted
    @Override
    public Boolean isCompleted() {
        if (super.isCompleted()) {
            if (super.getDoneDate().equals(LocalDate.now())) {
                return true;
            }
        }
        return false;
          
    }
    /**
     * Vaihtaa toistuvaistehtävän tilan suoritetuksi.
     * Asettaa myös seuraavan määräpäivän toistumistiheyden perusteella.
     */
    @Override
    public void setCompleted() {
        super.setCompleted();
        super.changeDueDate(super.getDoneDate().plusDays(recurringInterval));        
    }
    
    
    public void setRecurringInterval(int days) throws Exception {
        this.recurringInterval = days;
    }

    
    public int getRecurringInterval() {
        return recurringInterval;
    }

    
    

}