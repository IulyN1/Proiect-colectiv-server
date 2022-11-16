package repository;

import domain.*;
import org.springframework.stereotype.Component;
import repository.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Database containing reviews
 */
@Component
public class ReviewRepositoryDatabase implements ReviewRepository {
    private JdbcUtils dbUtils;

    /**
     * Constructor for repository using Spring beans
     * @param jdbcProps properties for database connection
     */
    public ReviewRepositoryDatabase(Properties jdbcProps) {
        dbUtils = new JdbcUtils(jdbcProps);
    }

    @Override
    public void test() {
        System.out.println("Consider yourself tested");
    }

    @Override
    public Review add(Review e) {
        return null;
    }

    @Override
    public Review delete(Review e) {
        return null;
    }

    @Override
    public Review find(Review e) {
        return null;
    }

    @Override
    public Review update(Review e) {
        return null;
    }


    @Override
    public List<Review> getAll() throws Exception {
        Connection con = dbUtils.getConnection();
        List<Review> reviews = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT * FROM Reviews");

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                int userId = result.getInt("userId");
                int productId = result.getInt("productId");
                String text = result.getString("text");
                int nrOfStars = result.getInt("nrOfStars");

                Review review = new Review(id, userId, productId, nrOfStars, text);
                reviews.add(review);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting all reviews!");
        }
        return reviews;
    }

    @Override
    public List<Review> getReviewsByProduct(int pid) throws Exception {
        Connection con = dbUtils.getConnection();
        List<Review> reviews = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT * FROM ProductsReviews WHERE productId=?");
            statement.setInt(1, pid);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int reviewId = result.getInt("reviewId");
                PreparedStatement statement2 = con.prepareStatement
                        ("SELECT * FROM Reviews WHERE id=?");
                statement2.setInt(1, reviewId);

                ResultSet result2 = statement2.executeQuery();
                result2.next();

                int id = result2.getInt("id");
                String text = result2.getString("text");
                int nrOfStars = result2.getInt("nrOfStars");
                result2.close();

                PreparedStatement statement3 = con.prepareStatement
                        ("SELECT * FROM UsersReviews WHERE reviewId=?");
                statement3.setInt(1, reviewId);

                ResultSet result3 = statement3.executeQuery();
                result3.next();

                int userId = result3.getInt("userId");
                result3.close();

                Review review = new Review(id, userId, pid, nrOfStars, text);
                reviews.add(review);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting reviews!");
        }
        return reviews;
    }

    @Override
    public Review addReview( Review review) throws Exception {
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("INSERT INTO Reviews (text, nrOfStars) VALUES (?, ?);");

            statement.setString(1, review.getText());
            statement.setInt(2, review.getNrOfStars());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    review.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating review failed, no ID obtained.");
                }
            }
            statement = con.prepareStatement
                    ("INSERT INTO ProductsReviews (productId, reviewId) VALUES (?, ?);");
            statement.setInt(1, review.getProductId());
            statement.setInt(2, review.getId());
            statement.executeUpdate();


             statement = con.prepareStatement
                    ("INSERT INTO UsersReviews (userId, reviewId) VALUES (?, ?);");
            statement.setInt(1, review.getUserId());
            statement.setInt(2, review.getId());

            statement.executeUpdate();
            return review;
        } catch (SQLException ex) {
            throw new Exception("Error adding review!");
        }
    }


    public Review updateReview(Review elem, int id) {
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preSmt=con.prepareStatement("UPDATE Reviews SET text = ?, nrOfStars = ? WHERE id = ?;")){
            preSmt.setString(1, elem.getText());
            preSmt.setInt(2, elem.getNrOfStars());
            preSmt.setLong(3, id);
            elem.setId(id);
            int result=preSmt.executeUpdate();

            return elem;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int size() {
        return -1;
    }
}