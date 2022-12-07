package domain;

import java.io.Serializable;

/**
 * Class that represents a product
 */
public class Product implements Serializable {
    private int id;
    private String name;
    private int price;
    private int nrInStock;

    public Product() {}

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Product(int id, String name, int price, int nrInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.nrInStock = nrInStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNrInStock() {
        return nrInStock;
    }

    public void setNrInStock(int nrInStock) {
        this.nrInStock = nrInStock;
    }
}
