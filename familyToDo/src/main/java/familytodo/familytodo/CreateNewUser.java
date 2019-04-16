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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author karhunko
 */
public class CreateNewUser {
    
    private ArrayList<String> lista;

    public CreateNewUser(ArrayList<String> lista) {
        this.lista = lista;
    }
    
        
    public Parent getCreateUserScreen() {
        
        // get user input for login
        Label useruidance = new Label("Ole hyvä ja valitse itsellesi: ");
        Label instructions = new Label("Käyttäjätunnus: ");        
        TextField username = new TextField();
        Label passwordGuide = new Label("Salasana: ");
        PasswordField passInput = new PasswordField();
        Label passwordGuideCheck = new Label("Salasana\nuudestaan: ");
        PasswordField passInputCheck = new PasswordField(); 
        
        
        Button getInput = new Button("Valmis!");
        
        
        Label rec = new Label("");
        rec.setWrapText(true);
        rec.setMaxWidth(170);
        
        // create places for user input elements
        GridPane loginGroup = new GridPane();        
        loginGroup.setAlignment(Pos.CENTER);
        loginGroup.setVgap(10);
        loginGroup.setHgap(10);
        loginGroup.setPadding(new Insets(10, 10, 10, 10));
        
        // (y,x)
        loginGroup.add(useruidance, 0, 0, 2, 1);
        loginGroup.add(instructions, 0, 1);
        loginGroup.add(username, 1, 1);
        loginGroup.add(passwordGuide, 0, 2);
        loginGroup.add(passInput, 1, 2);
        loginGroup.add(passwordGuideCheck, 0, 3);
        loginGroup.add(passInputCheck, 1, 3);
        //buttons
        loginGroup.add(getInput, 1, 4);
        
        //test
        loginGroup.add(rec, 1, 5);
        
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
                String received = "{" + user + " : " + pass + "}";
                lista.add(received);
                
                username.clear();
                passInput.clear();
                passInputCheck.clear();
                
                String sana = "";
                for (int i = 0; i < lista.size(); i++) {
                            sana += lista.get(i) + "\n";
                        }
                rec.setTextFill(Color.BLACK);
                rec.setText(sana);
            }
            
            
                    
        });
        
        return loginGroup;
    }
    
    
}
