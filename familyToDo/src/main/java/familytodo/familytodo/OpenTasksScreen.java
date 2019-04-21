/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package familytodo.familytodo;

import domain.RecurringTodo;
import domain.Todo;
import domain.User;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Collections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author karhunko
 */
public class OpenTasksScreen {
    
    private ArrayList<String> lista;
    private ArrayList<Todo> tasks;
    private VBox taskQueue;   
    private User loggedIn;
    
    public OpenTasksScreen(ArrayList<String> lista) {
        this.lista = lista;
        this.tasks = new ArrayList<>();
        taskQueue = new VBox();
        this.loggedIn = new User("pete", "petri-man");
        Todo pyykit = new Todo("pese pyykit", loggedIn);
        pyykit.changeDueDate(LocalDate.of(2019, 04, 16));
        Todo laksyt = new Todo("tee läksyt", loggedIn);
        laksyt.changeDueDate(LocalDate.parse("2019-04-17"));
        laksyt.setCompleted();
        RecurringTodo petaus = new RecurringTodo("petaa sänky", loggedIn, 1);
        this.tasks.add(new Todo("imuroi", loggedIn));
        this.tasks.add(new Todo("tee tiskit", loggedIn));
        this.tasks.add(new Todo("pyyhi pölyt", loggedIn));
        this.tasks.add(laksyt);
        this.tasks.add(pyykit); 
        this.tasks.add(petaus);
        
    }
    
        
    public Parent getOpenTaskScreen() {
        // get user input for login
        
        // add new task
        BorderPane table = new BorderPane();
        ScrollPane plate = new ScrollPane();        
        table.setCenter(plate);
        
        GridPane addTask = new GridPane();
        addTask.setHgap(5);
        addTask.setVgap(2);
        
        Label newTask = new Label("Lisää uusi tehtävä: ");
        newTask.setMinWidth(300);
        
        TextField taskName = new TextField();
        DatePicker deadline = new DatePicker();
        deadline.setMaxWidth(125);
        
        Label repeatText = new Label("Toistoväli: ");
        DatePicker nextOccurence = new DatePicker();
        nextOccurence.setMaxWidth(125);
        
        Button insertTask = new Button("Lisää!");
        
        
        addTask.setPadding(new Insets(5, 5, 5, 5));
        
        addTask.add(newTask, 0, 0, 2, 1);
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
                                            , this.loggedIn, (int) interval);
                    deadline.setValue(null);
                    nextOccurence.setValue(null);
                    taskName.clear();      
                    tasks.add(receivedTask);
                    
                } else if (deadline.getValue() != null) {
                    Todo receivedTask = new Todo(task, this.loggedIn
                                                , deadline.getValue());
                    tasks.add(receivedTask);
                    taskName.clear();      
                    deadline.setValue(null);
                    
                } else {
                    Todo receivedTask = new Todo(task, this.loggedIn);
                    tasks.add(receivedTask);
                    taskName.clear();                    
                    
                }
                getOpenTaskScreen();
            } 
        });
        
        
        table.setTop(addTask);
        
        taskQueue.getChildren().clear();
        Collections.sort(tasks);
        
        for (Todo x : this.tasks) {
            if (x instanceof RecurringTodo) {
                if (x.getDoneDate() == null 
                    || !x.getDoneDate().isBefore(LocalDate.now())) {
                    taskQueue.getChildren().add(taskRow(x));
                }
            } else {
                taskQueue.getChildren().add(taskRow(x));
            }
        }
        plate.setContent(taskQueue);
        return table;
    }
    
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
        });
        
        chgDueDate.setOnAction((event) -> {
            LocalDate newDate = chgDueDate.getValue();
            dueDate.setText(newDate.toString());
            task.changeDueDate(newDate);
            getOpenTaskScreen();
        });
        
        //box.getChildren().addAll(btnDone, taskName, dueDate, chgDueDate);        
        box.add(btnDone, 0, 0);
        box.add(taskName, 1, 0);
        box.add(doneDate, 3, 0);
        box.add(chgDueDate, 2, 0);
        return box;
    }
    
    
    
}
