package dao;

import domain.Todo;
import java.time.LocalDate;
import java.util.List;

//import domain.Todo;

public interface TodoDao extends  Dao<Todo, Integer> {
    //Todo create(Todo todo) throws Exception;

    void setCompleted() throws Exception;

    void changeTaskName(String task) throws Exception;

    void changeDueDate(LocalDate date) throws Exception;
    
    List<Todo> findDone() throws Exception;
    
}

