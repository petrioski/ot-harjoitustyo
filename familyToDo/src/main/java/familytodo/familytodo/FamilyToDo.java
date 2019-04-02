/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package familytodo.familytodo;

import domain.User;
import domain.Todo;

/**
 *
 * @author karhunko
 */
public class FamilyToDo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String tervehdys = "ohjelma käynnistyi";
        System.out.println(tervehdys);
        User person = new User("Testi Kayttaja", "testiK");
        
        Todo task1 = new Todo("imuroi", person);
        task1.setCompleted();
        
        System.out.println(task1.toString());
        
        
        
    }
    
}
