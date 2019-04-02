/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.time.LocalDate;

/**
 *
 * @author karhunko
 */
public class Todo {    
    private String task;
    private LocalDate endDate;
    private LocalDate startDate;
    private LocalDate doneDate;
    private Boolean completed;
    private User user;

    
    public Todo(String task, User user) {
        this.task = task;        
        this.user = user;
        this.completed = false;
    }
    
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Todo{" + ", task=" + task + ", endDate=" + endDate + ", startDate=" + startDate + ", doneDate=" + doneDate + ", completed=" + completed + ", user=" + user + '}';
    }
    
    
    
}
