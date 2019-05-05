package domain;

import dao.FakeSqlTodoDao;
import dao.FakeSqlUserDao;
import dao.FakeSqlUserPreferencesDao;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.Matchers;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


public class TodoServiceTest {
    TodoService fakeLogic;
    User testUser;
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    @Before
    public void setUp() throws Exception {
        File createdFolder = tempFolder.newFolder("testaus");
        String path = createdFolder.getPath() + "test33.db";
        Database testDB = new Database("jdbc:sqlite:"+path);
        
        FakeSqlUserDao userDao = new FakeSqlUserDao(testDB);
        FakeSqlTodoDao todoDao = new FakeSqlTodoDao(testDB);                
        FakeSqlUserPreferencesDao settingsDao = new FakeSqlUserPreferencesDao(testDB);
        fakeLogic = new TodoService(todoDao, userDao, settingsDao);
        testUser = fakeLogic.getCurrentUser();
    }
    
    @Test
    public void testNullUserAtStart() {
        assertThat(fakeLogic.getCurrentUser(), is(nullValue()));
    }
    
    @Test
    public void testMissingUserLogin() {
        Boolean loginResult = false;
        try {
            loginResult = fakeLogic.login("empty", "empty");
        } catch (Exception e) {

        }
        
        assertThat(fakeLogic.getCurrentUser(), is(nullValue()));
        assertThat(loginResult, is(false));
    }
    
    
    @Test
    public void testValidUserLogin() {
        Boolean loginResult = false;
        try {
            loginResult = fakeLogic.login("petri-man", "pass10");
        } catch (Exception e) {

        }
        
        assertThat(fakeLogic.getCurrentUser().getUsername(), is("petri-man"));
        assertThat(loginResult, is(true));
    }
    
    
    @Test
    public void testValidUserWithWrongPasswordLogin() {
        Boolean loginResult = false;
        try {
            loginResult = fakeLogic.login("petri-man", "pass10000");
        } catch (Exception e) {

        }
        
        assertThat(fakeLogic.getCurrentUser(), is(nullValue()));
        assertThat(loginResult, is(false));
    }
    
    @Test
    public void fetchOpenTasksWithNoUser() {
        List<Todo> receivedList = new ArrayList<>();
        
        try {
            receivedList = fakeLogic.getOpenAndRecentlyDone();
        } catch (Exception e) {

        }
        
        assertThat(receivedList.isEmpty(), is(true));
        
    }
    
    
    @Test
    public void fetchDoneTasksWithNoUser() {
        List<Todo> receivedList = new ArrayList<>();
        
        try {
            receivedList = fakeLogic.getDoneTasks();
        } catch (Exception e) {

        }
        
        assertThat(receivedList.isEmpty(), is(true));
        
    }
    
    @Test
    public void fetchOpenTasksWithLegitUser() {
        List<Todo> receivedList = new ArrayList<>();
        this.logUserIn();
        
        try {
            receivedList = fakeLogic.getOpenAndRecentlyDone();
        } catch (Exception e) {
            
        }
        
        assertThat(receivedList.isEmpty(), is(false));
        assertThat(receivedList.size(), is(7));
        
    }
    
    @Test
    public void fetchDoneTasksWithLegitUser() {
        List<Todo> receivedList = new ArrayList<>();
        this.logUserIn();
        
        try {
            receivedList = fakeLogic.getDoneTasks();
        } catch (Exception e) {
         
        }
        
        assertThat(receivedList.isEmpty(), is(false));
        assertThat(receivedList.size(), is(2));
        
    }
    
    
    @Test
    public void fetchOpenTasksWithTodayDones() {
        List<Todo> receivedList = new ArrayList<>();
         try {
            fakeLogic.login("testUser2", "passTest");
        } catch (Exception e) {
            
        }
        
        try {
            receivedList = fakeLogic.getOpenAndRecentlyDone();
        } catch (Exception e) {
            
        }
        
        assertThat(receivedList.isEmpty(), is(false));
        assertThat(receivedList.size(), is(1));
        
    }
    
    
    @Test
    public void fetchOnlyDoneTasksWithLegitUser() {
        List<Todo> receivedList = new ArrayList<>();
        
        try {
            fakeLogic.login("testUser", "passTest");
        } catch (Exception e) {
            
        }
        
        try {
            receivedList = fakeLogic.getOpenAndRecentlyDone();
        } catch (Exception e) {
            
        }
        
        assertThat(receivedList.isEmpty(), is(true));        
        
    }
    
    @Test
    public void addNewTask() throws Exception {
        logUserIn();
        
        int numberOfOldTasks = fakeLogic.getOpenAndRecentlyDone().size();
        Todo t = new Todo("buy something"
                    , fakeLogic.getCurrentUser().getId()
                    , new UserPreferences(fakeLogic.getCurrentUser().getId()));
        fakeLogic.addNewTask(t);
                
        
        int numberOfNewTasks = fakeLogic.getOpenAndRecentlyDone().size();
        int countChange = numberOfNewTasks - numberOfOldTasks;
        
        assertThat(countChange, is(1));
    }
    
    @Test 
    public void addNewUser() {
        Boolean result = false;
        Boolean resultCreation = false;
        
        try {
            result = fakeLogic.login("tester1", "tester3");
        } catch (Exception e) {
            
        }
        
        assertThat(result, is(false));
        
        try {
            resultCreation = fakeLogic.validUserCreation("tester1", "tester2"
                                        , "tester3", "tester3");
        } catch (Exception e) {
            
        }
        
        try {
            result = fakeLogic.login("tester2", "tester3");
        } catch (Exception e) {
            
        }
        
        assertThat(resultCreation, is(true));
        assertThat(result, is(true));
    }
    
    
    @Test
    public void addNewUserWithEmptyPassword() {
        Boolean result = false;
                      
        try {
            result = fakeLogic.validUserCreation("tester1", "tester2"
                                                 , "  ", "  ");
        } catch (Exception e) {
            
        }
        
        assertThat(result, is(false));
    }
    
    
    @Test
    public void userCreationShortName() {
        String name = "";
        String username = "aaaa";
        String pass = "abc";
        String pass2 = "abc";
        
        Boolean result = fakeLogic.validUserCreation(name, username
                                                    , pass, pass2);
        
        assertThat(result, is(false));
        
    }    
    
    
    @Test
    public void userCreationShortUsername() {
        String name = "aaaa";
        String username = "";
        String pass = "abc";
        String pass2 = "abc";
        
        Boolean result = fakeLogic.validUserCreation(name, username
                                                    , pass, pass2);
        
        assertThat(result, is(false));
        
    }  
    
    
    @Test
    public void userCreationNonMatchingPass() {
        String name = "aaaa";
        String username = "uuu";
        String pass = "abc";
        String pass2 = "abc2";
        
        Boolean result = fakeLogic.validUserCreation(name, username
                                                    , pass, pass2);
        
        assertThat(result, is(false));
        
    } 
    
    
    
    
    @Test
    public void addNewUserWithExistingUsername() {
        Boolean resultLogin = false;
        Boolean resultCreation = false;
                
                
        try {
            resultCreation = fakeLogic.addNewUser(new User("pete", 
                    "petri-man", "pass999"));
        } catch (Exception e) {
            
        }
        
               
        assertThat(resultCreation, is(false));
        assertThat(resultLogin, is(false));
    }
    
    @Test
    public void addUserWithNewUsername() {
         Boolean resultCreation = false;
                
                
        try {
            resultCreation = fakeLogic.addNewUser(new User("test", 
                    "newUser", "pass999"));
        } catch (Exception e) {
            
        }
        
        assertThat(resultCreation, is(true));
    }
    
    
    @Test
    public void checkIfUserExists() {                
        assertThat(fakeLogic.userExists("petri-man"), is(true));                
    }
    
    @Test
    public void logOut() { 
        this.logUserIn();
        assertThat(fakeLogic.getCurrentUser() != null, is(true));                
        assertThat(fakeLogic.getCurrentSettings() != null, is(true));  
        
        fakeLogic.logOut();
        
        assertThat(fakeLogic.getCurrentUser() != null, is(false));                
        assertThat(fakeLogic.getCurrentSettings() != null, is(false));   
    }
        
    
    
    @Test
    public void checkIfUserDoesNotExist() {
        assertThat(fakeLogic.userExists("testi-man"), is(false));
        assertThat(fakeLogic.userExists(""), is(false));
    }
    
    
    @Test
    public void changePreferences() {
        logUserIn();
        UserPreferences prefs = fakeLogic.getCurrentSettings();
        prefs.setDefaultDuration(10);
        fakeLogic.saveOrUpdatePrefences(prefs);
        fakeLogic.logOut();
        
        logUserIn();
        UserPreferences prefsNew = fakeLogic.getCurrentSettings();
        assertThat(prefsNew.getDefaultDuration(), is(10));
    }
    
    
    @Test
    public void updateTask() {
        logUserIn();
        Todo task = fakeLogic.getOpenAndRecentlyDone().get(0);
        Boolean statusOld = task.isCompleted();
        int taskId = task.getTaskId();
        task.toggleCompleted();
        fakeLogic.saveOrUpdateTask(task);
        
        List<Todo> all = fakeLogic.getOpenAndRecentlyDone();
        Todo updatedTask = null;
        for (Todo x : all) {
            if (x.getTaskId() == taskId) {
                updatedTask = x;
            }
        }
        
                            
        assertThat(Objects.equals(updatedTask.isCompleted()
                            , statusOld), is(false));
    }
    
    @Test
    public void deleteTask() {
        logUserIn();
        Todo task = fakeLogic.getOpenAndRecentlyDone().get(0);
        
        int taskId = task.getTaskId();
        
        fakeLogic.deleteTask(task);
        
        List<Todo> all = fakeLogic.getOpenAndRecentlyDone();
        Todo updatedTask = null;
        
        for (Todo x : all) {
            if (x.getTaskId() == taskId) {
                updatedTask = x;
            }
        }
        
                            
        assertThat(updatedTask
                            , is(nullValue()));
    }
    
    @Test
    public void addNewRecurringTask() {
        logUserIn();
        RecurringTodo roskat = new RecurringTodo("tee ruokaa"
                , fakeLogic.getCurrentId()
                , fakeLogic.getCurrentSettings());
        
        assertThat(roskat.getTaskId(), is(nullValue()));
        fakeLogic.addNewTask(roskat);
        
        List<Todo> all = fakeLogic.getOpenAndRecentlyDone();
        
        RecurringTodo addedTask = null;
        
        for (Todo x : all) {
            if (x.getTask().equals(roskat.getTask())) {
                addedTask = (RecurringTodo) x;
            }
        }
        
        assertThat(addedTask.getTaskId(), Matchers.greaterThan(0));
        
        addedTask.toggleCompleted();
        
        fakeLogic.saveOrUpdateTask(addedTask);
        
        List<Todo> newList = fakeLogic.getDoneTasks();
        Boolean updatedStatus = false;
        for (Todo x : newList) {
            if (x.getTaskId().equals(addedTask.getTaskId())) {
                updatedStatus = x.getCompleted();
            }
        }
        assertThat(updatedStatus, is(true));
        
    }
    
    
    @Test
    public void testUpdatingPreferences() {
        logUserIn();
        UserPreferences pref = fakeLogic.getCurrentSettings();
        pref.setDefaultDuration(100);        
        fakeLogic.saveOrUpdatePrefences(pref);
        pref.setRecurringInterval(99);
        fakeLogic.saveOrUpdatePrefences(pref);
        logOut();
        logUserIn();
        UserPreferences revised = fakeLogic.getCurrentSettings(); 
        
        assertThat(revised.getDefaultDuration(), is(100));
        assertThat(revised.getRecurringInterval(), is(99));
    }
    
    
    
    
    private void logUserIn() {
        try {
            fakeLogic.login("petri-man", "pass10");
        } catch (Exception e) {
            
        }
    }
    
    
}