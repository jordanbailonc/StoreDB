package cat.proven.storedb;

import cat.proven.storedb.controllers.MainController;
import cat.proven.storedb.model.Model;

public class Main {

    public static void main(String[] args) {
        Main ap = new Main();
        ap.run();
    }

    private void run() {
        Model model = new Model();
        MainController controller = new MainController(model);
        controller.getView().show();
        System.out.println("Goodbye!");
    }
    
}
