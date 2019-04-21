/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package familytodo.familytodo;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author karhunko
 */
public class LoginScreen {
    
    private ArrayList<String> lista;
    private Boolean inessa;
    private Scene taskit;
    private Stage window;
    
    public LoginScreen(ArrayList<String> lista) {
        this.lista = lista;
        inessa = false;
//        taskit = loggedIn;
//        window = stage;
    }
    
        
    public Parent getLoginScreen() {
        
        // get user input for login
        Label userGuidance = new Label("Ole hyvä ja kirjaudu sisään: ");
        Label instructions = new Label("Käyttäjänimi: ");        
        TextField username = new TextField();
        Label passwordGuide = new Label("Salasana: ");
        TextField passInput = new TextField();       
        
        
        Button getInput = new Button("Valmis!");
        
        
        Label rec = new Label("");
        rec.setTextFill(Color.BLACK);
        rec.setWrapText(true);
        rec.setMaxWidth(170);
        
        
        // create places for login elements
        GridPane loginGroup = new GridPane();
        
        
        loginGroup.setAlignment(Pos.CENTER);
        loginGroup.setVgap(10);
        loginGroup.setHgap(10);
        loginGroup.setPadding(new Insets(10, 10, 10, 10));
        
        // (x,y)
        loginGroup.add(userGuidance, 0, 0, 2, 1);
        loginGroup.add(instructions, 0, 1);
        loginGroup.add(username, 1, 1);
        loginGroup.add(passwordGuide, 0, 2);
        loginGroup.add(passInput, 1, 2);
        //buttons
        loginGroup.add(getInput, 1, 3);
        
        //test
        loginGroup.add(rec, 1, 4);
        
        getInput.setOnAction((event) -> {
            String user = username.getText();
            String pass = passInput.getText();
            
            if (!user.trim().equals("anna")) {
                String received = "{" + user + " : " + pass + "}";
                lista.add(received);

                username.clear();
                passInput.clear();

                String sana = "";
                for (int i = 0; i < lista.size(); i++) {
                    sana += lista.get(i) + "\n";
                }

                rec.setText(sana);
                return;
                
            }
            
            inessa = true;
            rec.setText("jotain " + inessa);
            window.setScene(taskit);
        });
        
        return loginGroup;
    }
    
    
}
