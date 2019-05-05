package domain;


import java.time.LocalDate;

public class RecurringTodo extends Todo  {
    private int recurringInterval;

    public RecurringTodo(String task, int user, UserPreferences settings) {
        super(task, user, settings);
        recurringInterval = settings.getRecurringInterval();
    }

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
    
    // set recurring freq after task name
    
    public String getTaskTitle() {
        if (super.getDoneDate() != null) {
            return super.getTask() 
                    + " (" + this.recurringInterval + " pv,"
                    + " edell. " + super.getDoneDate() + ")";
        }
        return super.getTask() + " (" + this.recurringInterval + " pv)";
    }
    
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
    
    @Override
    public void setCompleted() {
        super.setCompleted();
        super.changeDueDate(super.getDoneDate().plusDays(recurringInterval));        
    }
    
    
    public void setRecurringInterval(int days) throws Exception {
        this.recurringInterval = days;
    }

    /**
     * @return the recurringInterval
     */
    public int getRecurringInterval() {
        return recurringInterval;
    }

    
    

}