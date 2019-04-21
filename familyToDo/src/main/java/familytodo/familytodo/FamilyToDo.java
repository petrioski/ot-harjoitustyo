/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package familytodo.familytodo;

import domain.User;
import domain.RecurringTodo;
import domain.Todo;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author karhunko
 */
public class FamilyToDo extends Application {
    //private Scene loginScene;
    private OpenTasksScreen tasks;
    
    ArrayList<String> lista = new ArrayList();
    Boolean ok = false;
    
    @Override
    public void start(Stage stage) throws Exception {
        int width = 600;
        int heigth = 400;
        
        //construct required screens
        tasks = new OpenTasksScreen(lista);
        LoginScreen login = new LoginScreen(lista);
        CreateNewUser newUser = new CreateNewUser(lista);
        
        TabPane tabs = new TabPane();
        Tab login1 = new Tab();
        login1.setText("Start");
        tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        Tab taskList = new Tab();
        taskList.setText("Tehtävät");
        
        // basic composition
        BorderPane layout = new BorderPane();
        HBox placeholder = new HBox();
        placeholder.setPadding(new Insets(20, 20, 20, 20));
        placeholder.setSpacing(10);
        
        Button createUser = new Button("Luo uusi tunnus");
        Button loginExisting = new Button("Kirjaudu");
        createUser.setMinWidth(150);
        loginExisting.setMinWidth(150);
        
        //button 
        placeholder.getChildren().add(createUser);
        layout.setTop(placeholder);
        layout.setCenter(login.getLoginScreen());        
        login1.setContent(layout);
        
        // second tab
        BorderPane choresLayout = new BorderPane();
        HBox placeholder2 = new HBox();
        placeholder2.setPadding(new Insets(20, 20, 20, 20));
        placeholder2.setSpacing(10);
        
        Button logOut = new Button("Kirjaudu ulos");
        logOut.setMinWidth(150);
        
        placeholder2.getChildren().add(createUser);
        choresLayout.setTop(placeholder);
        
        choresLayout.setCenter(tasks.getOpenTaskScreen());
        taskList.setContent(choresLayout);
        
        tabs.getTabs().add(login1);
        tabs.getTabs().add(taskList);
        //Scene loginScene = new Scene(layout, width, heigth);
        Scene loginScene = new Scene(tabs, width, heigth);
        
        createUser.setOnAction((event) -> {
            layout.setCenter(newUser.getCreateUserScreen());            
            placeholder.getChildren().remove(createUser);
            placeholder.getChildren().add(loginExisting);
        });
        
        loginExisting.setOnAction((event) -> {            
            placeholder.getChildren().remove(loginExisting);
            placeholder.getChildren().add(createUser);
            layout.setCenter(login.getLoginScreen());
        });
        
        stage.setTitle("Kirjaudu sisään");
                
        stage.setScene(loginScene);
                
        stage.show();
         
    }
    
    @Override
    public void stop() {      
        System.out.println("sovellus sulkeutuu");
    } 
    
    protected void displayInitialScene() {
        
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
        String tervehdys = "ohjelma käynnistyi";
        System.out.println(tervehdys);
        User person = new User("Testi Kayttaja", "testiK");
        
        Todo task1 = new Todo("imuroi", person);
        task1.setCompleted();
        
        RecurringTodo task2 = new RecurringTodo("tee tiskit", person, 1);

        
        
        
        
        
    }
    
}

