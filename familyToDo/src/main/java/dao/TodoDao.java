package dao;

import java.time.LocalDate;

//import domain.Todo;

public interface TodoDao {
    //Todo create(Todo todo) throws Exception;

    void setCompleted() throws Exception;

    void changeTaskName(String task) throws Exception;

    void changeDueDate(LocalDate date) throws Exception;
    
    
}

