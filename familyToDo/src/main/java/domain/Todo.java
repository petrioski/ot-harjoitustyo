/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.time.LocalDate;

import dao.TodoDao;

/**
 *
 * @author karhunko
 */
public class Todo implements TodoDao, Comparable<Todo> {    
    private String task;
    private LocalDate dueDate;
    private LocalDate startDate;
    private LocalDate doneDate;
    private Boolean completed;    
    private User user;

    
    public Todo(String task, User user) {
        this.task = task;        
        this.user = user;
        this.completed = false;
        this.dueDate = LocalDate.now().plusDays(2);
    }
    
    public Todo(String task, User user, LocalDate dueDate) {
        this.task = task;        
        this.user = user;
        this.completed = false;
        this.dueDate = dueDate;
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

    @Override
    public void setCompleted() {
        this.completed = true;
        this.doneDate = LocalDate.now();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public int compareTo(Todo next) {
        if (this.isCompleted().equals(next.isCompleted())) {
            if (this.dueDate.equals(next.dueDate)) {
                return this.task.compareTo(next.task);
            }
            return this.dueDate.compareTo(next.dueDate);                    
        } else if (this.isCompleted() && !next.isCompleted()) {
            return 1;
        }
        return -1;
    }
    
    /**
     * @return the doneDate
     */
    public LocalDate getDoneDate() {
        return doneDate;
    }

    /**
     * @param doneDate the doneDate to set
     */
    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    /**
     * @return the completed
     */
    public Boolean getCompleted() {
        return completed;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public void toggleCompleted() {
        if (!isCompleted()) {
            setCompleted();
        } else {
            this.completed = !this.completed;
            changeDueDate(LocalDate.now().plusDays(2));
        }
        
    }
    
    @Override
    public String toString() {
        return "Todo{" + "task=" + task + ", endDate=" + dueDate 
                + ", startDate=" + startDate + ", doneDate=" + doneDate 
                + ", completed=" + completed + ", user=" + user.toString() + '}';
    }
    
}
