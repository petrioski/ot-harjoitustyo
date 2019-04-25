package domain;


import java.time.LocalDate;

public class RecurringTodo extends Todo  {
    private int recurringInterval;

    public RecurringTodo(String task, User user) {
        super(task, user);
        recurringInterval = 7;
    }

    public RecurringTodo(String task, User user, int days) {
        super(task, user);
        recurringInterval = days;
    }
    
    // set recurring freq after task name
    @Override
    public String getTask() {
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
        //super.toggleCompleted();
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

    @Override
    public String toString() {
        return super.toString() + " repeat interval " + this.recurringInterval;
    }

    

}