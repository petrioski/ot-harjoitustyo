
package ui;

import dao.*;
import domain.*;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Sovelluksen käyttöliittymästä vastaava luokka 
 * 
 */
public class FamilyToDo extends Application {
    
    private TodoService logic;

    private Stage mainStage;
    
    private VBox openTaskQueue = new VBox();
    private VBox closedTaskQueue = new VBox();
    private BorderPane choresLayout = new BorderPane();
    private BorderPane archiveLayout = new BorderPane();
    private BorderPane loginPageLayout = new BorderPane();
    private BorderPane settingsPageLayout = new BorderPane();
    
    private TabPane tabs = new TabPane();
    private Tab loginTab = new Tab();        
    private Tab taskListTab = new Tab();        
    private Tab doneTasksTab = new Tab();
    private Tab settingsTab = new Tab();
                
    private SingleSelectionModel<Tab> selectionModel = tabs.getSelectionModel();
    
    /**
     * Suorittaa alustavat toiminnot ohjelman käynnistyessä
     * 
     */
    
    @Override
    public void init() throws Exception {
        Database database = new Database("jdbc:sqlite:tasks.db");
        SqlTodoDao todoDao = new SqlTodoDao(database);
        SqlUserDao userDao = new SqlUserDao(database);
        SqlUserPreferencesDao settingsDao = new SqlUserPreferencesDao(database);
        this.logic = new TodoService(todoDao, userDao, settingsDao);        
    }
    
    /**
     * Metodi luo uloskirjautumisnäkymän, kun käyttäjä on kirjautunut 
     * onnistuneesti sisään
     * 
     */
    
    private Node getLoggedInScreen() {
        // create places for login elements
        GridPane logOutForm = new GridPane();
                   
        logOutForm.setAlignment(Pos.CENTER);
        logOutForm.setVgap(10);
        logOutForm.setHgap(10);
        logOutForm.setPadding(new Insets(10, 10, 10, 10));
        
        Button logOutButton = new Button("Kirjaudu ulos");
        
        String loggedInUser = "";
        
        loggedInUser = "Olet kirjautunut käyttäjänimellä: "  
                        + logic.getCurrentUser();
        
        
        Label loggedInText = new Label(loggedInUser);
        loggedInText.setTextFill(Color.BLACK);
        loggedInText.setWrapText(true);
        loggedInText.setMaxWidth(170);
        
        logOutForm.add(loggedInText, 0, 1);
        logOutForm.add(logOutButton, 0, 2);
        
            
        logOutButton.setOnAction((event) -> {
            this.logic.logOut();
            mainStage.setTitle("FamilyToDo: " + "Kirjaudu sisään");
            
            this.updateAllUserTabs();
            
            loginTab.setContent(loginPageLayout);
        });
        
        return logOutForm;
    }
    
    
    
    /**
     * Sovelluksen aloitusnäkymä, josta pääsee kirjautumaan sisään tai
     * luomaan uuden käyttäjän
     */
    
    private Node getLoginScreen() {
                                                       
        Label userGuidance = new Label("Ole hyvä ja kirjaudu sisään: ");
        Label instructions = new Label("Käyttäjätunnus: ");        
        TextField username = new TextField();
        Label passwordGuide = new Label("Salasana: ");
        PasswordField passInput = new PasswordField();
        
        
        Button getLoginInputButton = new Button("Valmis!");
        Button createNewUserButton = new Button("Luo uusi käyttäjä");
        
        
        Label responseText = new Label("");
        responseText.setTextFill(Color.BLACK);
        responseText.setWrapText(true);
        responseText.setMaxWidth(170);
        
        
        // create places for login elements
        GridPane loginForm = new GridPane();
      

        loginForm.setAlignment(Pos.CENTER);
        loginForm.setVgap(10);
        loginForm.setHgap(10);
        loginForm.setPadding(new Insets(10, 10, 10, 10));
        
        // (x,y)
        loginForm.add(userGuidance, 0, 0, 2, 1);
        loginForm.add(instructions, 0, 1);
        loginForm.add(username, 1, 1);
        loginForm.add(passwordGuide, 0, 2);
        loginForm.add(passInput, 1, 2);
        

        
        //add buttons for user interaction
        loginForm.add(createNewUserButton, 1, 4);        
        loginForm.add(getLoginInputButton, 1, 3);
        
        // give user feedback
        loginForm.add(responseText, 1, 5);
        
        
        // existing user login event
        getLoginInputButton.setOnAction((event) -> {
            String user = username.getText();
            String pass = passInput.getText();
            
            
            boolean successLogin = false;
            try {
                successLogin = logic.login(user, pass);
            } catch (Exception ex) {
                             
            }
            
            if (!successLogin) {
                username.clear();
                passInput.clear();

                String sana = "Väärä käyttäjätunnus tai salasana";

                responseText.setText(sana);
                return;
            } 
            
            username.clear();
            passInput.clear();
            
            
            mainStage.setTitle("FamilyToDo: " 
                                + logic.getCurrentUser().getName()
                                + " kirjautunut sisään");
            
            this.updateAllUserTabs();
            
            selectionModel.select(taskListTab);

        });
        
        
        createNewUserButton.setOnAction((event) -> {
            loginPageLayout.setCenter(getCreateUserScreen());
            loginTab.setContent(loginPageLayout);
        });
        
        
        return loginForm;
    }
    
    
    /**
     * Uuden käyttäjän luova näkymä
     */
    
    
    private Node getCreateUserScreen() {
        
        // get user input for login
        Label useruidance = new Label("Ole hyvä ja valitse itsellesi: ");
        Label ownNameGuide = new Label("Oma nimesi: ");        
        TextField ownName = new TextField();    
        Label instructions = new Label("Käyttäjätunnus: ");        
        TextField username = new TextField();
        Label passwordGuide = new Label("Salasana: ");
        PasswordField passInput = new PasswordField();
        Label passwordGuideCheck = new Label("Salasana\nuudestaan: ");
        PasswordField passInputCheck = new PasswordField(); 
        
        
        Button getInput = new Button("Luo tunnus!");
        Button goToLoginPage = new Button("Kirjautumissivulle!");
        
        Label rec = new Label("");
        rec.setWrapText(true);
        rec.setMaxWidth(170);
        
        // create places for user input elements
        GridPane createUserForm = new GridPane();        
        createUserForm.setAlignment(Pos.CENTER);
        createUserForm.setVgap(10);
        createUserForm.setHgap(10);
        createUserForm.setPadding(new Insets(10, 10, 10, 10));
        
        // cordinates are in order (x,y)        
        createUserForm.add(useruidance, 0, 0, 2, 1);
        createUserForm.add(ownNameGuide, 0, 1);
        createUserForm.add(ownName, 1,1, 2,1);
        createUserForm.add(instructions, 0, 2);
        createUserForm.add(username, 1, 2, 2, 1);
        createUserForm.add(passwordGuide, 0, 3);
        createUserForm.add(passInput, 1, 3, 2, 1);
        createUserForm.add(passwordGuideCheck, 0, 4);
        createUserForm.add(passInputCheck, 1, 4, 2, 1);
        
        //buttons
        createUserForm.add(getInput, 1, 5);
        createUserForm.add(goToLoginPage, 2, 5);
        
        //show test result to user
        createUserForm.add(rec, 1, 6, 4, 4);
        
        getInput.setOnAction((event) -> {
            String name = ownName.getText();
            String user = username.getText();
            String pass = passInput.getText();
            String checkPass = passInputCheck.getText();
            rec.setTextFill(Color.RED);
            
            if (user.length() <= 2) {
                rec.setText("Hups..Käyttäjätunnus liian lyhyt");                
                username.clear();

            } else if (logic.userExists(user)){    
                rec.setText("Hups..Käyttäjätunnus on varattu");
                username.clear();
            } else if (!pass.equals(checkPass)
                       || pass.length() <= 2) {                
                rec.setText("Hups..Salasana liian lyhyt \n"
                        + "tai kentät ei täsmänneet");                
                passInput.clear();
                passInputCheck.clear();
            } else {
                String received = "Uusi tunnus luotu, "
                                + "voit kirjautua nyt sisään";
                
                logic.validUserCreation(name, user, pass, checkPass);
                
                
                ownName.clear();
                username.clear();
                passInput.clear();
                passInputCheck.clear();
                
                String sana = received;                
                
                rec.setTextFill(Color.BLACK);
                rec.setText(sana);
            }
            
        });
        
        goToLoginPage.setOnAction((event) -> {            
            username.clear();
            passInput.clear();
            passInputCheck.clear();
            rec.setText("");
            
            loginPageLayout.setCenter(getLoginScreen());
            loginTab.setContent(loginPageLayout);
        });
        
        return createUserForm;
    }
    
            
    /**
     * Näkymä listaa tehdyiksi merkityt tehtävät
     */
            
    private Node getDoneTaskScreen() {
        
        
        // add new task
        BorderPane table = new BorderPane();
        ScrollPane plate = new ScrollPane();        
        table.setCenter(plate);
        
        GridPane addTask = new GridPane();
        addTask.setHgap(5);
        addTask.setVgap(2);
        
        
        if (logic.getCurrentUser() != null) {
            table.setTop(addTask);
            
        } else {
            table.setTop(new Label(""));
            plate.setContent(new Label("Kirjaudu sisään nähdäksesi tehtävät"));
            return table;
        }
        

        closedTaskQueue.getChildren().clear();
        List<Todo> tasklist = logic.getDoneTasks();
        Collections.sort(tasklist);
        
        
        for (Todo x : tasklist) {
            if (x instanceof RecurringTodo) {
                if (x.getDoneDate() == null 
                    || !x.getDoneDate().isBefore(LocalDate.now())) {
                    closedTaskQueue.getChildren().add(taskRow(x));
                }
            } else {
                closedTaskQueue.getChildren().add(taskRow(x));
            }
        }
        
        plate.setContent(closedTaskQueue);
        
        return table;
    }
            
    
    /**
     * Näkymä listaa tekemättömät tehtävät
     */
    
    private Node getOpenTaskScreen() {
                        
        BorderPane table = new BorderPane();
        ScrollPane plate = new ScrollPane();        
        table.setCenter(plate);
        
        Label newTaskLabel = new Label("Lisää uusi tehtävä: ");
        newTaskLabel.setMinWidth(300);
        
        TextField taskName = new TextField();
        DatePicker deadline = new DatePicker();
        deadline.setMaxWidth(125);
        
        Label repeatText = new Label("Toistumispäivä: ");
        DatePicker nextOccurence = new DatePicker();
        nextOccurence.setMaxWidth(125);
        
        Button insertTask = new Button("Lisää!");
        CheckBox checkRecurring = new CheckBox();
        
        GridPane addTask = new GridPane();
        addTask.setHgap(15);
        addTask.setVgap(5);        
        addTask.setPadding(new Insets(5, 0, 5, 0));
        
        addTask.add(newTaskLabel, 0, 0, 2, 1);
        addTask.add(taskName, 0, 1, 2, 1);
        addTask.add(new Label("Määräpäivä:"), 2, 0);
        addTask.add(deadline, 2, 1);
        addTask.add(repeatText, 4, 0);
        addTask.add(nextOccurence, 4, 1);        
        addTask.add(new Label("Toistuva?"), 5, 0);
        addTask.add(checkRecurring, 5, 1);
        addTask.add(insertTask, 6, 1);
        
        
        insertTask.setOnAction((event) -> {
            String task = taskName.getText().trim();
            if (task.length() >= 3) {               
                if (deadline.getValue() != null 
                        && nextOccurence.getValue() != null) {                   
                    long interval = DAYS.between(deadline.getValue()
                                            , nextOccurence.getValue());                           
                    RecurringTodo receivedTask = new RecurringTodo(task
                                            , logic.getCurrentId()
                                            , logic.getCurrentSettings()
                                            , (int) interval);
                    deadline.setValue(null);
                    nextOccurence.setValue(null);
                    taskName.clear();     
                    checkRecurring.setSelected(false);
                    logic.addNewTask(receivedTask);
                
                } else if (deadline.getValue() != null 
                            && !checkRecurring.isSelected()) {
                    Todo receivedTask = new Todo(task, logic.getCurrentId()                                                
                                                ,logic.getCurrentSettings());
                    receivedTask.changeDueDate(deadline.getValue());
                    logic.addNewTask(receivedTask);
                    taskName.clear();                          
                    deadline.setValue(null);
                    
                } else if (deadline.getValue() != null 
                            && checkRecurring.isSelected()) {
                    RecurringTodo receivedTask = new RecurringTodo(task
                                            , logic.getCurrentId()
                                            , logic.getCurrentSettings());
                    
                    receivedTask.changeDueDate(deadline.getValue());
                    
                    logic.addNewTask(receivedTask);
                    
                    checkRecurring.setSelected(false);
                    taskName.clear();                          
                    deadline.setValue(null);
                } else if (checkRecurring.isSelected()) {
                    RecurringTodo receivedTask = new RecurringTodo(task
                                            , logic.getCurrentId()
                                            , logic.getCurrentSettings());
                                                            
                    logic.addNewTask(receivedTask);
                    checkRecurring.setSelected(false);
                    taskName.clear();                          
                    
                } else {
                    Todo receivedTask = new Todo(task, logic.getCurrentId()
                                                 , logic.getCurrentSettings());
                    logic.addNewTask(receivedTask);
                    taskName.clear();   
                    deadline.setValue(null);
                    nextOccurence.setValue(null);
                    
                }
                getOpenTaskScreen();
            } 
        });
        
        
        if (logic.getCurrentUser() != null) {
            table.setTop(addTask);
            
        } else {
            table.setTop(new Label(""));
            plate.setContent(new Label("Kirjaudu sisään nähdäksesi tehtävät"));
            return table;
        }
        
        
        openTaskQueue.getChildren().clear();
        List<Todo> tasklist = logic.getOpenAndRecentlyDone();
        Collections.sort(tasklist);
        
        
        for (Todo x : tasklist) {
            if (x instanceof RecurringTodo) {
                if (x.getDoneDate() == null 
                    || !x.getDoneDate().isBefore(LocalDate.now())) {
                    openTaskQueue.getChildren().add(taskRow(x));
                }
            } else {
                openTaskQueue.getChildren().add(taskRow(x));
            }
        }
        
        plate.setContent(openTaskQueue);
        
        return table;
    }
    
    /**
     * Näkymä vastaa oletusasetusten näyttämisestä käyttäjälle
     *      
     */
    private Node getSettingsScreen() {
        BorderPane settingsPane = new BorderPane();
        ScrollPane settingsElementsBox = new ScrollPane();        
        
        settingsPane.setTop(new Label(""));
        settingsPane.setCenter(settingsElementsBox);
        
        
        // stop here if user not logged in
        if (logic.getCurrentUser() == null) {
            settingsElementsBox.setContent(new Label("Kirjaudu sisään nähdäksesi "
                    + "asetukset"));
            return settingsPane;
        }
        
        // continue with current user's settings
        GridPane userSettings = new GridPane();
        
        // heading row
        BorderPane guidanceBar = new BorderPane();
        guidanceBar.setPadding(new Insets(10, 0, 10, 0));
        
        Label prefGuide = new Label("Vaihda uusien tehtävien oletusasetukset:");
        guidanceBar.setLeft(prefGuide);
        
        Button saveSettingsBtn = new Button("Tallenna");
        guidanceBar.setRight(saveSettingsBtn);
        
        settingsPane.setTop(guidanceBar);
        
        
        
        int minHeight = 30;
        Label deadline = new Label("Tehtävien oletuskesto (pv)");
        deadline.setMinHeight(minHeight);
        Label repeatFreq = new Label("Toistuvien tehtävien uusimisväli (pv)");
        repeatFreq.setMinHeight(minHeight);

        
        TextField defaultDeadline = new TextField("");                        
        defaultDeadline.setText(""+this.logic.getDefaultDuration());
        
        TextField defaultFreq = new TextField("");
        defaultFreq.setText(""+logic.getRepeatInterval());
        userSettings.setVgap(10);
        userSettings.setHgap(30);
        userSettings.setPadding(new Insets(10, 5, 10, 5));
        userSettings.add(deadline, 0,0);
        userSettings.add(defaultDeadline, 1,0);
        userSettings.add(repeatFreq, 0,1);
        userSettings.add(defaultFreq, 1,1);

        
        settingsElementsBox.setContent(userSettings);
        
        
        saveSettingsBtn.setOnAction((event) -> {
            Integer newDeadline = logic.getDefaultDuration();
            Integer newFreq = logic.getRepeatInterval();
            
            try {
                int received = Integer.parseInt(defaultDeadline.getText());
                
                if (received >= 0) {
                    newDeadline = received;
                }
                
            } catch (Exception e) {
                
            }
            
            try {
                int received = Integer.parseInt(defaultFreq.getText());
                
                if (received >= 0) {
                    newFreq = received;
                }
                
            } catch (Exception e) {
                
            }
            
            UserPreferences newPref = new UserPreferences(logic.getCurrentId()
                                                , newDeadline, newFreq);
            
            logic.saveOrUpdatePrefences(newPref);
            this.updateAllUserTabs();
        });
        
        
        return settingsPane;
    }
    

    /**
     * Metodi luo yhden rivin tehtävälistaus -näkymiin, kuten avoimet 
     * tehtävät ja tehdyt tehtävät
     */
    
    private Node taskRow(Todo task) {
        //HBox box = new HBox(10);
        GridPane box = new GridPane();
        
        box.setMaxWidth(Region.USE_COMPUTED_SIZE);
        box.setVgap(10);
        box.setHgap(20);
        box.setPadding(new Insets(1, 0, 10, 10));
        
        String taskTitle = task.getTask();
        if (task instanceof RecurringTodo) {
            taskTitle = ((RecurringTodo) task).getTaskTitle();
        }
        
        Label taskName = new Label(taskTitle);
        taskName.prefHeight(28);     
        taskName.setFont(Font.font(16.0));
        taskName.setMinWidth(300);
        
        DatePicker chgDueDate = new DatePicker(task.getEndDate());
        chgDueDate.setMaxWidth(125);
        
        CheckBox btnDone = new CheckBox();
        Label dueDate = new Label(task.getEndDate().toString());
        Label doneDate = new Label(""); 
        Button deleteTask = new Button("Poista");
        
        
        
        if (task.isCompleted() && task instanceof Todo) {
            btnDone.fire();
        }
        
        btnDone.setOnAction((ActionEvent event) -> {            
            task.toggleCompleted();            
            this.logic.saveOrUpdateTask(task);
            getOpenTaskScreen();
            getDoneTaskScreen();
        });
        
        chgDueDate.setOnAction((event) -> {
            LocalDate newDate = chgDueDate.getValue();
            dueDate.setText(newDate.toString());
            task.changeDueDate(newDate);
            this.logic.saveOrUpdateTask(task);
            getOpenTaskScreen();
            getDoneTaskScreen();
            
        });
        
        deleteTask.setOnAction((event) -> {
            this.logic.deleteTask(task);
            getOpenTaskScreen();
            getDoneTaskScreen();
        });
               
        box.add(btnDone, 0, 0);
        box.add(taskName, 1, 0);
        box.add(doneDate, 3, 0);
        box.add(chgDueDate, 2, 0);
        box.add(deleteTask, 4, 0);
        return box;
    }
    
    
    private void updateAllUserTabs() {
        // tabs with always same content
        choresLayout.setCenter(getOpenTaskScreen());
        archiveLayout.setCenter(getDoneTaskScreen());        
        settingsPageLayout.setCenter(getSettingsScreen());
        
        // check start tab content based on logged in status
        if (this.logic.getCurrentUser() != null) {
            loginPageLayout.setCenter(getLoggedInScreen());
        } else {
            loginPageLayout.setCenter(getLoginScreen());
        }
    }
    
    
    /**
      * Metodi aloittaa graafisen käyttöliittymän suorituksen 
      * ja kutsuu ensimmäisenä sisäänkirjautumissivua
      */   
    
    
    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        
        int width = 700;
        int heigth = 600;
                                
  
        loginTab.setText("Aloitus");
        tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        taskListTab.setText("Avoimet");
        doneTasksTab.setText("Tehdyt");      
        settingsTab.setText("Asetukset");
        
        
        loginPageLayout.setCenter(getLoginScreen());
        loginTab.setContent(loginPageLayout);
        
                
        choresLayout.setCenter(getOpenTaskScreen());
        taskListTab.setContent(choresLayout);
        
        
        archiveLayout.setCenter(getDoneTaskScreen());
        doneTasksTab.setContent(archiveLayout);
        
        settingsPageLayout.setCenter(getSettingsScreen());
        settingsTab.setContent(settingsPageLayout);
        
        tabs.getTabs().add(loginTab);
        tabs.getTabs().add(taskListTab);
        tabs.getTabs().add(doneTasksTab);
        tabs.getTabs().add(settingsTab);
        
        
        
        Scene loginScene = new Scene(tabs, width, heigth);                
        
        mainStage.setTitle("FamilyToDo: " + "Kirjaudu sisään");
                
        mainStage.setScene(loginScene);
                
        mainStage.show();        
                 
    }
    
    /**
     * Metodi suorittaa ohjelman lopetustoiminnot sulkeuduttaessa
     */
    @Override
    public void stop() {      

    } 
    
    
    public static void main(String[] args) {        
        launch(args);                
    }
    
}

