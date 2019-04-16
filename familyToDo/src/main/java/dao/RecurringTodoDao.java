package dao;

public interface RecurringTodoDao extends TodoDao {
    void setRecurringInterval(int days) throws Exception;
    
}