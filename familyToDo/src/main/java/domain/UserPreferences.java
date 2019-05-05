
package domain;


public class UserPreferences {
    private Integer userId;
    private Integer defaultDuration;
    private Integer recurringInterval;

    public UserPreferences(int user) {
        this.userId = user;
        defaultDuration = 2;
        recurringInterval = 7;
    }

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
