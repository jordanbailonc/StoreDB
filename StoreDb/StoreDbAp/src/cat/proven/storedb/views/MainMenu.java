
package cat.proven.storedb.views;

public class MainMenu extends Menu {

    public MainMenu() {
        setTitle("Product application with database");
        addOption(new Option("Exit", "exit"));
        addOption(new Option("List all products", "listallproducts"));
        addOption(new Option("Find product by code", "schprodcode"));
        addOption(new Option("List products with low stock", "schprodlowstock"));
        addOption(new Option("Add a new product", "addproduct"));
        addOption(new Option("Modify product", "modifyproduct"));
        addOption(new Option("Remove product", "removeproduct"));
    }
    
}
