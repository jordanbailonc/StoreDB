package cat.proven.storedb.model;

import java.util.Objects;

/**
 * ADT (abstract data type) of object product.
 * @author ProvenSoft
 */
public class Product {
    //attributes
    private long id;
    private String code;
    private String description;
    private double price;
    private int stock;
    
    //constructors
    
    public Product(long id, String code, String description, double price, int stock) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Product(long id) {
        this.id = id;
    }
    
    public Product() {
    }
    
    public Product(Product other) {
        this.id = other.id;
        this.code = other.code;
        this.description = other.description;
        this.price = other.price;
        this.stock = other.stock;
    }
    
    public long getId() {
        return id;
    }

    //accessors
    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    //methods.

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product {");
        sb.append("[id="); sb.append(id); sb.append("]");
        sb.append("[code="); sb.append(code); sb.append("]");
        sb.append("[description="); sb.append(description); sb.append("]");
        sb.append("[price="); sb.append(price); sb.append("]");
        sb.append("[stock="); sb.append(stock); sb.append("]");
        sb.append("}");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (this == obj) { //same object.
            result = true;
        } else {
            if (obj == null) { //null object.
                result = false;
            } else {
                if (obj instanceof Product) {
                    Product other = (Product) obj;
                    //compare
                    result = (this.id == other.id);
                }
            }
        }
        
        return result;
    }
    
    


    
    
}
