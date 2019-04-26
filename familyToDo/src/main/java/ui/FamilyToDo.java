/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dao.SqlTodoDao;
import dao.SqlUserDao;
import domain.RecurringTodo;
import domain.Todo;
import domain.TodoService;
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
    
    private TabPane tabs = new TabPane();
    private Tab loginTab = new Tab();        
    private Tab taskListTab = new Tab();        
    private Tab doneTasksTab = new Tab();
                
    private SingleSelectionModel<Tab> selectionModel = tabs.getSelectionModel();
    
    /**
     * Suorittaa alustavat toiminnot ohjelman käynnistyessä
     * 
     */
    
    @Override
    public void init() throws Exception {
        SqlTodoDao todoDao = new SqlTodoDao();
        SqlUserDao userDao = new SqlUserDao();
        this.logic = new TodoService(todoDao, userDao);        
    }
    
    /**
     * Sovelluksen aloitusnäkymä, josta pääsee kirjautumaan sisään tai
     * luomaan uuden käyttäjän
     */
    
    private Node getLoginScreen() {
                                                       
        Label userGuidance = new Label("Ole hyvä ja kirjaudu sisään: ");
        Label instructions = new Label("Käyttäjänimi: ");        
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
        
        //buttons
        loginForm.add(getLoginInputButton, 1, 3);
        loginForm.add(createNewUserButton, 1, 4);
        
        //test
        loginForm.add(responseText, 1, 5);
        
        
        // existing user login event
        getLoginInputButton.setOnAction((event) -> {
            String user = username.getText();
            String pass = passInput.getText();
            
            
            boolean successLogin = false;
            try {
                successLogin = logic.login(user, pass);
            } catch (Exception ex) {
                System.out.println("Exepction tapahtui");                
            }
            
            if (!successLogin) {
                username.clear();
                passInput.clear();

                String sana = "Väärä salasana";

                responseText.setText(sana);
                return;
            } 
            
            username.clear();
            passInput.clear();
            
            responseText.setText("Olet kirjautunut käyttäjänimellä: "  + logic.getCurrentUser());
            mainStage.setTitle("FamilyToDo: " + logic.getCurrentUser() + " kirjautunut sisään");
            
            choresLayout.setCenter(getOpenTaskScreen());
            archiveLayout.setCenter(getDoneTaskScreen());
            taskListTab.setContent(choresLayout);
            tabs.getTabs().add(taskListTab);
            
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
    
    // new user file transactions
    public Node getCreateUserScreen() {
        
        // get user input for login
        Label useruidance = new Label("Ole hyvä ja valitse itsellesi: ");
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
        
        // (x,y)
        createUserForm.add(useruidance, 0, 0, 2, 1);
        createUserForm.add(instructions, 0, 1);
        createUserForm.add(username, 1, 1, 2, 1);
        createUserForm.add(passwordGuide, 0, 2);
        createUserForm.add(passInput, 1, 2, 2, 1);
        createUserForm.add(passwordGuideCheck, 0, 3);
        createUserForm.add(passInputCheck, 1, 3, 2, 1);
        
        //buttons
        createUserForm.add(getInput, 1, 4);
        createUserForm.add(goToLoginPage, 2, 4);
        
        //show test result to user
        createUserForm.add(rec, 1, 5, 4, 4);
        
        getInput.setOnAction((event) -> {
            String user = username.getText();
            String pass = passInput.getText();
            String checkPass = passInputCheck.getText();
            rec.setTextFill(Color.RED);
            
            if (user.trim().length() <= 2) {
                rec.setText("Hups..Käyttäjätunnus liian lyhyt");                
                username.clear();
                passInput.clear();
                passInputCheck.clear();    
                
            } else if (!pass.equals(checkPass)) {                
                rec.setText("Hups..Salasanat eivät täsmänneet");                
                passInput.clear();
                passInputCheck.clear();
            } else {
                String received = "Uusi tunnus luotu: {" + user + " : " + pass + "}";
                
                username.clear();
                passInput.clear();
                passInputCheck.clear();
                
                String sana = received;
                // TODO: 
                // add file save transaction to save new user
                // *******************************************
                
                rec.setTextFill(Color.BLACK);
                rec.setText(sana);
            }
            
        });
        
        goToLoginPage.setOnAction((event) -> {
            loginPageLayout.setCenter(getLoginScreen());
            loginTab.setContent(loginPageLayout);
            username.clear();
            passInput.clear();
            passInputCheck.clear();
            rec.setText("");
        });
        
        return createUserForm;
    }
    
            
    /**
     * Näkymä listaa tehdyiksi merkityt tehtävät
     */
            
    private Node getDoneTaskScreen() {
        // get user input for login
        
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
        
//        VBox taskQueue = new VBox();
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
        // get user input for login
        
        // add new task
        BorderPane table = new BorderPane();
        ScrollPane plate = new ScrollPane();        
        table.setCenter(plate);
        
        Label newTaskLabel = new Label("Lisää uusi tehtävä: ");
        newTaskLabel.setMinWidth(300);
        
        TextField taskName = new TextField();
        DatePicker deadline = new DatePicker();
        deadline.setMaxWidth(125);
        
        Label repeatText = new Label("Toistoväli: ");
        DatePicker nextOccurence = new DatePicker();
        nextOccurence.setMaxWidth(125);
        
        Button insertTask = new Button("Lisää!");
        
        
        GridPane addTask = new GridPane();
        addTask.setHgap(5);
        addTask.setVgap(2);        
        addTask.setPadding(new Insets(5, 5, 5, 5));
        
        addTask.add(newTaskLabel, 0, 0, 2, 1);
        addTask.add(taskName, 1, 1, 2, 1);
        addTask.add(new Label("Määräpäivä:"), 2, 0);
        addTask.add(deadline, 2, 1);
        addTask.add(repeatText, 4, 0);
        addTask.add(nextOccurence, 4, 1);
        addTask.add(insertTask, 5, 1);

        
        insertTask.setOnAction((event) -> {
            String task = taskName.getText().trim();
            if (task.length() >= 3) {               
                if (deadline.getValue() != null 
                        && nextOccurence.getValue() != null) {                   
                    long interval = DAYS.between(deadline.getValue()
                                            , nextOccurence.getValue());                           
                    RecurringTodo receivedTask = new RecurringTodo(task
                                            , logic.getCurrentUser(), (int) interval);
                    deadline.setValue(null);
                    nextOccurence.setValue(null);
                    taskName.clear();      
                    logic.addNewTask(receivedTask);
                    
                } else if (deadline.getValue() != null) {
                    Todo receivedTask = new Todo(task, logic.getCurrentUser()
                                                , deadline.getValue());
                    logic.addNewTask(receivedTask);
                    taskName.clear();      
                    deadline.setValue(null);
                    
                } else {
                    Todo receivedTask = new Todo(task, logic.getCurrentUser());
                    logic.addNewTask(receivedTask);
                    taskName.clear();                    
                    
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
     * Metodi luo yhden rivin tehtävä näkymiin
     */
    
    private Node taskRow(Todo task) {
        //HBox box = new HBox(10);
        GridPane box = new GridPane();
        
        box.setMaxWidth(Region.USE_COMPUTED_SIZE);
        box.setVgap(10);
        box.setHgap(10);
        box.setPadding(new Insets(1, 0, 10, 10));
        
        Label taskName = new Label(task.getTask());
        taskName.prefHeight(28);     
        taskName.setFont(Font.font(16.0));
        taskName.setMinWidth(300);
        
        DatePicker chgDueDate = new DatePicker(task.getEndDate());
        chgDueDate.setMaxWidth(125);
        
        CheckBox btnDone = new CheckBox();
        Label dueDate = new Label(task.getEndDate().toString());
        Label doneDate = new Label(""); 
        
        
        if (task instanceof RecurringTodo && task.getDoneDate() != null) {            
            doneDate = new Label(task.getDoneDate().toString());
        } 
        
        if (task.isCompleted() && task instanceof Todo) {
            btnDone.fire();
        }
        
        btnDone.setOnAction((ActionEvent event) -> {
            task.toggleCompleted();
            getOpenTaskScreen();
            getDoneTaskScreen();
        });
        
        chgDueDate.setOnAction((event) -> {
            LocalDate newDate = chgDueDate.getValue();
            dueDate.setText(newDate.toString());
            task.changeDueDate(newDate);
            getOpenTaskScreen();
            getDoneTaskScreen();
            
        });
        
               
        box.add(btnDone, 0, 0);
        box.add(taskName, 1, 0);
        box.add(doneDate, 3, 0);
        box.add(chgDueDate, 2, 0);
        return box;
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
        
        
        loginPageLayout.setCenter(getLoginScreen());
        loginTab.setContent(loginPageLayout);
        
                
        choresLayout.setCenter(getOpenTaskScreen());
        taskListTab.setContent(choresLayout);
        
        
        archiveLayout.setCenter(getDoneTaskScreen());
        doneTasksTab.setContent(archiveLayout);
        
        tabs.getTabs().add(loginTab);
        tabs.getTabs().add(taskListTab);
        tabs.getTabs().add(doneTasksTab);
        
        choresLayout.setCenter(getOpenTaskScreen());
        archiveLayout.setCenter(getDoneTaskScreen());
        
        
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
        System.out.println("sovellus sulkeutuu");
    } 
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);                
    }
    
}

