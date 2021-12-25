package cat.proven.storedb.model;

import cat.proven.storedb.model.persist.ProductDao;
import java.util.List;

public class Model {

    private final ProductDao productDao;

    public Model() {
        this.productDao = new ProductDao();
    }
    
    /**
     * searches all products in database.
     * @return list of all products or null in case of error.
     */
    public List<Product> searchAllProducts() {
        List<Product> result = null;
        result = productDao.selectAll();
        return result;
    }
    
    /**
     * searches a product with the given code.
     * @param code the code to search.
     * @return a product with the given code or null in case of error or not found.
     */
    public Product searchProductByCode(String code) {
        Product result = null;
        result = productDao.selectWhereCode(code);
        return result;
    }
    
    /**
     * adds a product to database. Prevents code duplicates.
     * @param product the product to add.
     * @return 1 if successfully added, 0 otherwise.
     */
    public int addProduct(Product product) {
        int result = 0;
        result=productDao.addProduct(product);
        return result;
    }
    
    /**
     * list all product under an specific amount of stock input by user.
     * @param stock the amount search by the user
     * @return a list of products equals and under that stock
     */
    public List<Product> productStockUnder(int stock) {
        List<Product> result = null;
        result = productDao.productsUnderStock(stock);
        return result;
    }
    
    /**
     * modifies the data of a product that exists into DB. 
     * @param id the identifier from a product
     * @return 1 if succesfully modified, 0 if sqlException
     */
    public int modifyProduct(Product id){
        int result =0;
        result =productDao.modifyProduct(id);
        return result;
        
    }
    
    /**
     * checks if the id recived matches with an existing product
     * @param id the product identifier
     * @return true if DB have a product with that id
     */
    public boolean checkExistentId(long id){
        boolean resp;
        resp= productDao.selectWhereId(id);
        return resp;
    }
    
    /**
     * deletes product getting the id from the product
     * @param id the product identifier
     * @return 1 if succesfully delete 0 if it doesn't works
     */
    public int deleteProduct(long id){
        int result=0;
        result=productDao.deleteProduct(id);
        return result;
    }
    
    

}
