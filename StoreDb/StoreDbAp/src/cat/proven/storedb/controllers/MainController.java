package cat.proven.storedb.controllers;

import cat.proven.storedb.model.Model;
import cat.proven.storedb.model.Product;
import cat.proven.storedb.views.MainView;
import java.util.List;

public class MainController {

    private final Model model;
    private final MainView view;

    public MainController(Model model) {
        this.model = model;
        this.view = new MainView(this);
    }

    public Model getModel() {
        return model;
    }

    public MainView getView() {
        return view;
    }

    public void processAction(String action) {
        switch (action) {
            case "exit": //exit.
                exitApplication();
                break;
            case "listallproducts": //list all products.
                listAllProducts();
                break;
            case "schprodcode": //search product by code.
                listProductByCode();
                break;
            case "schprodlowstock":
                listLowStock();
                break;
            case "addproduct":
                getData_addProduct();
                break;
            case "modifyproduct":
                modifyProduct();
                break;
            case "removeproduct":
                removeProduct();
                break;
            default:
                view.displayMessage("Not implemented yet!");
                break;
        }
    }

    private void exitApplication() {
        view.setExit(true);
    }

    /**
     * lists all products.
     */
    private void listAllProducts() {
        List<Product> data = model.searchAllProducts();
        if (data != null) {
            view.displayProductTable(data);
        } else { // error.
            view.displayMessage("Error retrieving data");
        }
    }

    /**
     * asks the user to input a code, searches a product with that code, and
     * reports result to user.
     */
    private void listProductByCode() {
        String code = view.prompt("Input code: ");
        if (code != null) {
            Product p = model.searchProductByCode(code);
            if (p != null) {
                view.displayProductForm(p);
            } else {
                view.displayMessage("Product not found");
            }
        } else {
            view.displayMessage("Error reading code");
        }
    }

    /**
     * ask the user to input the data to get a new Product.
     * When is created it's added to the DB or tell the user
     * the problem to add it inot the DB.
     */
    private void getData_addProduct() {
        try {
            Product prodNew = createProduct();
            int res = model.addProduct(prodNew);
            if(res==0)view.displayMessage("Error into the addition");
            else if(res==1) view.displayMessage("Product added into the DB");
        } catch (NumberFormatException e) {
            view.displayMessage("NO ha se ha encontrado numero en la DB");
        } catch (Exception e){
            view.displayMessage("Error en l'actualització");
        }
    }

    /**
     * ask the user to input a code. When it gets the code print the product 
     * info in case that exist. If it not exists tell the user that the code 
     * doesn't match with any product 
     */
    private void listLowStock() {
        String stock = view.prompt("Input code: ");
        if (stock != null) {
            try {
                int numberStock = Integer.parseInt(stock);
                List<Product> data = model.productStockUnder(numberStock);
                if (data != null) {
                    view.displayProductTable(data);
                } else { // error.
                    view.displayMessage("Error retrieving data");
                }
            }catch(NumberFormatException e){
                view.displayMessage("The content must be numeric");
            }catch (Exception e) {
                view.displayMessage("Error en l'actualització");
            }

        }
    }

    /**
     * this method ask the user an id to look for a produc into db; if it exist
     * it ask the user the data to modify it, if not tells the user that it
     * doesn't exist into DB.
     */
    private void modifyProduct() {
        String txt = view.prompt("Input the id from the product to modify:\n");
        if (txt != null) {
            try {
                long id = Long.parseLong(txt);
                boolean flag = model.checkExistentId(id);
                if (flag) {
                    Product a = addDatatoModify(id);
                    int resp = model.modifyProduct(a);
                    if (resp == 0) {
                        view.displayMessage("Eror during the update (change of data).");
                    } else if (resp == 1) {
                        view.displayMessage("Article successfully modified");
                    }
                }else   view.displayMessage("Input Id not found into DB");
            }catch (NumberFormatException e){
                    view.displayMessage("Content must be numeric");
            }catch (Exception e) {
                System.out.println("Error durant la modificació");
            }
        }
    }

    /**
     * this method ask the user an id; if it's found into DB it ask a 
     * confirmation to remove from DB, if it doesn't exist into DB tell to
     * the user.
     */
    private void removeProduct() {
        String txt = view.prompt("Write the id from the product to delete");
        if(txt!=null){
            try{
                long id= Long.parseLong(txt);
                boolean flag=model.checkExistentId(id);
                if(flag){
                    if(userConfirmation()){
                        model.deleteProduct(id);
                        view.displayMessage("The product with Id "+txt+" has been deleted from the DB");
                    }else view.displayMessage("Operation canceled by the User");
                }
            }catch(Exception e){
                view.displayMessage("Error during the excecution");
                view.displayMessage(e.toString());
            }
        }
    }

    /**
     * it asks the necesary data to create a new product to add into DB
     * @return a new Product (ready to be added)
     */
    private Product createProduct() {
        try {
            long id = 0;
            String code = "code";
            String description;
            double price;
            int stock;
            code += code+view.prompt("Input the code(2 num) of the new product:\n");
            description = view.prompt("Input the description about the new product:\n");
            price = Double.parseDouble(view.prompt("Input the price (numeric type):\n"));
            stock =Integer.parseInt(view.prompt("Input the stock of this article:\n"));
            Product productToAdd = new Product(id, code, description, price, stock);
            return productToAdd;
        }catch(NumberFormatException e){
            view.displayMessage("Content must be numeric");
            return null;
        }catch (Exception e) {
            view.displayMessage("Error en la introducció de la dada");
            view.displayMessage("No possibility to added the product");
            return null;
        }
    }

    /**
     * ask the user the new data to be corrected into an existing product in DB
     * @param id the way to select a specific product
     * @return a product the data Modified
     */
    private Product addDatatoModify(long id) {
        try {
            String code="code";
            String description;
            double price;
            int stock;
            code += view.prompt("Input the code(2 num) of the new product:\n");
            description =  view.prompt("Input the description about the new product:\n");
            price = Double.parseDouble(view.prompt("Input the price (numeric type):\n"));
            stock = Integer.parseInt(view.prompt("Input the stock of this article:\n"));
            Product productToAdd = new Product(id, code, description, price, stock);
            return productToAdd;
        }catch(NumberFormatException e){
            view.displayMessage("Cotent must be numeric");
            return null;
        }catch (Exception e) {
            view.prompt("Error introducing data");
            view.displayMessage(e.toString());
            return null;
        }
    }
    
    /**
     * ask to user a confirmation to ejecute another action
     * @return true if confirms, false if not.
     */
    private boolean userConfirmation(){
        boolean resp=false;
        String userAns = view.prompt("Are you sure of this action?(Yes(y)/No(n))");
        if(userAns.equalsIgnoreCase("y")|userAns.equalsIgnoreCase("yes")){
            resp=true;
        }else{
            return resp;
        }
        return resp;
    }

}
