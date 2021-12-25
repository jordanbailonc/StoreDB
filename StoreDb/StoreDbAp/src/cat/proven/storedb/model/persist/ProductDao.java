package cat.proven.storedb.model.persist;

import cat.proven.storedb.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDao {

    private DbConnect dbConnect;
    private final String QUERY_SELECT_ALL = "select * from products";
    private final String QUERY_SELECT_WHERE_CODE = "select * from products where code = ?";
    private final String QUERY_STOCK_UNDER = "select * from products where stock<=?";
    private final String QUERY_ADD_PRODUCT = "insert into products values (null, ?, ?, ?, ?)";
    private final String QUERY_MODIFY_BY_ID = "update products set code=?, description=?, price=?, stock=? where id=?";
    private final String QUERY_SELECT_WHERE_ID="select * from products where id = ?";
    private final String QUERY_DELETE_PRODUCT_BY_ID="delete from products where id=?";

    public ProductDao() {
        dbConnect = null;
        try {
            dbConnect = new DbConnect();
            dbConnect.loadDriver();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * gets all products from database.
     *
     * @return list of products or null in case of error.
     */
    public List<Product> selectAll() {
        List<Product> result = new ArrayList<>();
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(QUERY_SELECT_ALL);
                while (rs.next()) {
                    Product p = fromResultSet(rs);
                    if (p != null) {
                        result.add(p);
                    }
                }
            }

        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
            result = null;
        }
        return result;
    }

    /**
     * converts resultset entry to product.
     *
     * @param rs resultset to get data from.
     * @return product with data in current position of resultset.
     */
    private Product fromResultSet(ResultSet rs) throws SQLException {
        Product p = null;
        long id = rs.getLong("id");
        String code = rs.getString("code");
        String description = rs.getString("description");
        double price = rs.getDouble("price");
        int stock = rs.getInt("stock");
        p = new Product(id, code, description, price, stock);
        return p;
    }

    /**
     * gets a product getting the product code.
     * @param code the product code to find into DB
     * @return a product with all data if code matches, if not a null product
     */
    public Product selectWhereCode(String code) {
        Product p = null;
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                PreparedStatement st = conn.prepareStatement(QUERY_SELECT_WHERE_CODE);
                st.setString(1, code);
                ResultSet rs = st.executeQuery();
                boolean success = rs.next();
                if (!success) {
                    System.out.println("Input code doesn't exist");
                }
                p = fromResultSet(rs);
            }

        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
            p = null;
        }
        return p;
    }

    /**
     * list the product with same and under stock amount into DB.
     * @param stock the amount searched
     * @return a list of the products equals or under stock amount or null list
     */
    public List<Product> productsUnderStock(int stock) {
        List<Product> result = new ArrayList<>();
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                PreparedStatement st = conn.prepareStatement(QUERY_STOCK_UNDER);
                st.setInt(1, stock);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Product p = fromResultSet(rs);
                    if (p != null) {
                        result.add(p);
                    }
                }
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
            result = null;
        }
        return result;
    }

    /**
     * adds a product into the DB
     * @param pro the one to be added
     * @return 1 if succesfully added or 0 if not added into DB
     */
    public int addProduct(Product pro) {
        int result = 0;
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                PreparedStatement st = conn.prepareStatement(QUERY_ADD_PRODUCT);
                st.setString(1, pro.getCode());
                st.setString(2, pro.getDescription());
                st.setDouble(3, pro.getPrice());
                st.setInt(4, pro.getStock());
                result = st.executeUpdate();
            }
        } catch (SQLException e) {
            result = 0;
            System.out.println(e);
        }
        return result;

    }

    /**
     * modifies the data from an existing product in DB
     * @param prod the one to be modified
     * @return 1 if succesfuly modified or 0 if error into types input
     */
    public int modifyProduct(Product prod) {
        int result = 0;
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                PreparedStatement st = conn.prepareStatement(QUERY_MODIFY_BY_ID);
                st.setString(1, prod.getCode());
                st.setString(2, prod.getDescription());
                st.setDouble(3, prod.getPrice());
                st.setInt(4, prod.getStock());
                st.setLong(5, prod.getId());
                result = st.executeUpdate();
            }
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }

    /**
     * select a product into DB based on the id
     * @param id the product identifier
     * @return true if exist, false if id doesn't match with any product
     */
    public boolean selectWhereId(long id) {
        boolean flag = false;
        try{
            Connection conn = dbConnect.getConnection();
            if(conn!=null){
                PreparedStatement  st =conn.prepareStatement(QUERY_SELECT_WHERE_ID);
                st.setLong(1,id);
                ResultSet result=st.executeQuery();
                while (result.next()) {
                    Product p = fromResultSet(result);
                    if (p != null) {
                        flag=true;
                    }
                }
            }
        }catch(Exception e){
        flag=false;
    }
        return flag;
    }
    
    /**
     * deletes a product from DB based on the id
     * @param id the product identifier
     * @return 1 if succesfully added, 0 if type input error
     */
    public int deleteProduct(long id){
        int result=-1;
        try {
            Connection conn = dbConnect.getConnection();
            if (conn != null) {
                PreparedStatement st = conn.prepareStatement(QUERY_DELETE_PRODUCT_BY_ID);
                st.setLong(1,id);
                result = st.executeUpdate();
            }
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }

}
