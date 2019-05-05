
package dao;


import domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class FakeSqlTodoDao implements TodoDao {

    private Database database;
    
               
    public FakeSqlTodoDao(Database database) throws Exception {
        this.database = database;
        User testCase = new User(1, "testPerson", "doWork", "pass123");  
        Integer loggedIn = 1;
        UserPreferences pref = new UserPreferences(testCase.getId());
        Todo pyykit = new Todo("pese pyykit", loggedIn, pref);
        pyykit.changeDueDate(LocalDate.of(2019, 04, 16));
        Todo homework = new Todo("tee läksyt", loggedIn, pref);
        homework.changeDueDate(LocalDate.parse("2019-04-17"));
        homework.toggleCompleted();
        RecurringTodo petaus = new RecurringTodo("petaa sänky", loggedIn, pref, 1);
        RecurringTodo roskat = new RecurringTodo("vie roskat", loggedIn, pref, 1);
        roskat.toggleCompleted();
        this.addOne(new Todo("imuroi", loggedIn, pref));
        this.addOne(new Todo("tee tiskit", loggedIn, pref));
        this.addOne(new Todo("pyyhi pölyt", loggedIn, pref));
        this.addOne(homework);
        this.addOne(pyykit); 
        this.addOne(petaus);
        this.addOne(roskat);
        
        Todo oldTask = new Todo("makaronilaatikko", 2, pref);
        oldTask.toggleCompleted();
        oldTask.setDoneDate(LocalDate.now().minusDays(2));
        this.addOne(oldTask);
        
        Todo oldTask2 = new Todo("makaronilaatikko2", 3, pref);
        oldTask2.toggleCompleted();
        this.addOne(oldTask2);
        
        
    }
    
    
    

    
    
    @Override
    public Todo findOne(Integer key) throws SQLException {
        String sql = "SELECT * FROM Tasks \n"
                    + "WHERE id = ?";
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, key);
            
            ResultSet res = stmt.executeQuery();
            
            if (res.next()) {
                if (res.getInt("recurringInterval") > 0) {
                    RecurringTodo received = parseRecurringTodo(res);
                    return received;
                } else {
                    Todo received = parseNormalTask(res);
                    return received;
                }
            } 
        }
        return null;
    }

    private RecurringTodo parseRecurringTodo(ResultSet res) throws SQLException {
        RecurringTodo received;
        received = new RecurringTodo(
                        res.getInt("id"), 
                        res.getString("task"),
                        res.getInt("user"),
                        parseDate(res.getString("dueDate")),
                        parseDate(res.getString("startDate")),
                        parseDate(res.getString("doneDate")),
                        res.getBoolean("completed"),
                        res.getInt("recurringInterval"));
        return received;
    }
    
    private Todo parseNormalTask(ResultSet res) throws SQLException {
        Todo received = new Todo(res.getInt("id"), 
                        res.getString("task"),
                        res.getInt("user"),
                        parseDate(res.getString("dueDate")),
                        parseDate(res.getString("startDate")),
                        parseDate(res.getString("doneDate")),
                        res.getBoolean("completed"));
        return received;
    }
    
    @Override
    public List<Todo> findAll()  throws SQLException {
        List<Todo> all = new ArrayList<>();
        String searchUser = "SELECT * FROM Tasks";
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(searchUser);
            
            
            ResultSet res = stmt.executeQuery();
            
            while (res.next()) {  
                if (res.getInt("recurringInterval") > 0) {
                    RecurringTodo received;
                    received = parseRecurringTodo(res);

                    all.add(received);
                } else {
                    Todo received = parseNormalTask(res);
                    
                    all.add(received);
                }
            }
        }

        return all;
    }
    
    @Override
    public List<Todo> findDone(Integer user) throws SQLException {
        String sql = "SELECT * FROM Tasks \n"
                    + "WHERE completed = 1 AND user = ?";
        List<Todo> doneTasks = new ArrayList<>();
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user);
            
            ResultSet res = stmt.executeQuery();
            
            while (res.next()) {
                if (res.getInt("recurringInterval") > 0) {
                    RecurringTodo received = parseRecurringTodo(res);
                    doneTasks.add(received);
                } else {
                    Todo received = parseNormalTask(res);
                    doneTasks.add(received);
                }
            } 
        }

        return doneTasks;
    }
    
    @Override
    public void addOne(Todo task) throws SQLException {
        if (task instanceof RecurringTodo)  {
            executeInsertRecurringTask(task, insertRecurringTask());
                    
        } else {
            String insertSQL = insertNormalTask();
        
            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement(insertSQL);
                stmt.setString(1, task.getTask());
                stmt.setInt(2, task.getUser());
                stmt.setString(3, dateToText(task.getEndDate()));
                stmt.setString(4, dateToText(task.getStartDate()));
                stmt.setString(5, dateToText(task.getDoneDate()));
                stmt.setBoolean(6, task.getCompleted());                
                stmt.executeUpdate();
            }
        }           
    }
    
    private String insertRecurringTask() {
        String insertSQL = "INSERT INTO Tasks \n"
                + "(task, user, dueDate, \n"
                + "startDate, doneDate, \n"
                + "completed, recurringInterval) \n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return insertSQL;
    }
    
    private void executeInsertRecurringTask(Todo task, String insertSQL) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(insertSQL);
            stmt.setString(1, task.getTask());
            stmt.setInt(2, task.getUser());
            stmt.setString(3, dateToText(task.getEndDate()));
            stmt.setString(4, dateToText(task.getStartDate()));
            stmt.setString(5, dateToText(task.getDoneDate()));
            stmt.setBoolean(6, task.getCompleted());
            stmt.setInt(7, recurringInt(task));
            stmt.executeUpdate();
        }
    }
    
    private String insertNormalTask() {
        String insertSQL = "INSERT INTO Tasks \n"
                + "(task, user, dueDate, \n"
                + "startDate, doneDate, \n"
                + "completed) \n"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        return insertSQL;
    }
    
    
    @Override
    public Todo saveOrUpdate(Todo task) throws SQLException {
        
        if (task.getTaskId() == null) {
            addOne(task);
        } else {
            update(task);
        }
        
        return null;
    }
    
    public void update(Todo task) throws SQLException {
        
        if (task instanceof RecurringTodo)  {
            updateRecurring(task);
        } else {
            updateNormalTask(task);            
        }
    }
    
    private void updateNormalTask(Todo task) throws SQLException {
        String insertSQL = "UPDATE Tasks \n"
                    + "SET task = ?, user = ?, \n"
                        + "dueDate = ?, startDate = ?, \n"
                        + "doneDate = ? , \n"
                        + "completed = ? \n"                        
                    + "WHERE id = ?";
            
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(insertSQL);
            stmt.setString(1, task.getTask());
            stmt.setInt(2, task.getUser());
            stmt.setString(3, dateToText(task.getEndDate()));
            stmt.setString(4, dateToText(task.getStartDate()));
            stmt.setString(5, dateToText(task.getDoneDate()));
            stmt.setBoolean(6, task.getCompleted());                
            stmt.setInt(7, task.getTaskId());

            stmt.executeUpdate();
        }
    }
    private void updateRecurring(Todo task) throws SQLException {
        RecurringTodo t = (RecurringTodo) task;

        String insertSQL = sqlQueryForRecurringTaskUpdate();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(insertSQL);
            stmt.setString(1, t.getTask());
            stmt.setInt(2, t.getUser());
            stmt.setString(3, dateToText(t.getEndDate()));
            stmt.setString(4, dateToText(t.getStartDate()));
            stmt.setString(5, dateToText(t.getDoneDate()));
            stmt.setBoolean(6, t.getCompleted());
            stmt.setInt(7, t.getRecurringInterval());
            stmt.setInt(8, task.getTaskId());

            stmt.executeUpdate();
        }
    }
    
    private String sqlQueryForRecurringTaskUpdate() {
        String insertSQL = "UPDATE Tasks \n"
                + "SET task = ?, \n"
                    + "user = ?, \n"
                    + "dueDate = ?, \n"
                    + "startDate = ?, \n"
                    + "doneDate = ? , \n"
                    + "completed = ?, \n"
                    + "recurringInterval = ? \n"
                + "WHERE id = ?";  
        return insertSQL;
    }
    
    
    @Override
    public void delete(Integer key) throws Exception {
        String deleteSql = "DELETE FROM Tasks WHERE id = ?";
        
        try (Connection conn = database.getConnection()) {
            
            PreparedStatement stmt = conn.prepareStatement(deleteSql);
            stmt.setInt(1, key);
                        
            stmt.executeUpdate();
        }
    }
    
    private LocalDate parseDate(String date) {
        if (date != null) {
            return LocalDate.parse(date);
        }
        return null;
    }
    
    private String dateToText(LocalDate date) {
        if (date != null) {
            return date.toString();
        }
        return null;
    }
    
    private Integer recurringInt(Todo t) {
        if (t instanceof RecurringTodo) {
            return ((RecurringTodo) t).getRecurringInterval();
        } 
        return null;
    }
    
}
