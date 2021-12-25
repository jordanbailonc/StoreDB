/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.proven.storedb.views;

import cat.proven.storedb.controllers.MainController;
import cat.proven.storedb.model.Product;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author mati
 */
public class MainView {

    private final MainController controller;
    private boolean exit;
    private MainMenu mainMenu;

    public MainView(MainController controller) {
        this.controller = controller;
        this.mainMenu = new MainMenu();
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
    
    public void show() {
        this.exit = false;
        do {
            mainMenu.show();
            String action = mainMenu.getSelectedOptionActionCommand();
            if (action != null) {
                controller.processAction(action);
            }
            
        } while (!exit);
    }

    /**
     * display message.
     * @param message the message to display. 
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * displays a list of products.
     * @param data the list of products to display.
     */
    public void displayProductTable(List<Product> data) {
        if (data != null) {
            for (Product p : data) {
                System.out.println(p);
            }
        }
    }
    
    /**
     * displays product information.
     * @param product to display
     */
    public void displayProductForm(Product product) {
        System.out.println(product);
    }
    
    /**
     * prompts a message and reads an answer from user.
     * @param message the message to prompt.
     * @return user's answer or null in case of error.
     */
    public String prompt(String message) {
        System.out.print(message);
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }
    
   
    
}
