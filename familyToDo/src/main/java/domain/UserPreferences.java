
package domain;

/**
 * Luokka kuvaa käyttäjän oletusasetuksia ja käytetään sovelluslogiikan
 * yhteydessä kirjautuneen käyttäjän muutoksien toteuttamiseen.
 * 
 */
public class UserPreferences {
    private Integer userId;
    private Integer defaultDuration;
    private Integer recurringInterval;
    
    /**
     * Luo uudet oletusasetukset käyttäjän id:n perusteella ensimmäistä kertaa.
     * Käyttää oletuksen järjestelmän asetuksia 
     * @param user käyttäjän id
     */
    public UserPreferences(int user) {
        this.userId = user;
        defaultDuration = 2;
        recurringInterval = 7;
    }
    
    /**
     * Luo uudet oletusasetukset käyttäjän palautteen perusteella.
     * 
     * @param user käyttäjän id
     * @param defaultDuration tehtävän oletusmääräpäivä
     * @param recurringInterval tehtävän oletustoistumistiheys
     */
    public UserPreferences(int user, Integer defaultDuration, int recurringInterval) {
        this.userId = user;
        this.defaultDuration = defaultDuration;
        this.recurringInterval = recurringInterval;
    }

    public Integer getDefaultDuration() {
        return defaultDuration;
    }

    public void setDefaultDuration(Integer defaultDuration) {
        this.defaultDuration = defaultDuration;
    }

    public int getRecurringInterval() {
        return recurringInterval;
    }

    public void setRecurringInterval(int recurringInterval) {
        this.recurringInterval = recurringInterval;
    }

    public Integer getUserId() {
        return userId;
    }


    
    
    
}
