package domain;

import java.io.Serializable;

/**
 * Class that represents a review
 * The review has: id, userId representing the user that made the review
 *                 productId representing the product which is reviewing
 */
public class Review implements Serializable{

    private int id;
    private int userId;
    private int productId;
    private int nrOfStars;
    private String text;

    public Review() {}

    public Review(int id, int userId, int productId, int nrOfStars, String text) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.nrOfStars = nrOfStars;
        this.text= text;
    }

    public Review(int userId, int productId, String text) {
        this.userId = userId;
        this.productId = productId;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId ) {
        this.userId = userId;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getText(){ return this.text; }

    public void setText(String text){ this.text = text; }

    public int getNrOfStars() {
        return nrOfStars;
    }

    public void setNrOfStars(int nrOfStars) {
        this.nrOfStars = nrOfStars;
    }
}
