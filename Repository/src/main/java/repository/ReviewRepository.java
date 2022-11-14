package repository;

import domain.Review;

import java.util.List;

/**
 * Custom interface for the review repository
 */
public interface ReviewRepository extends Repository<Review>{
    void test();

    List<Review> getReviewsByProduct(int pid) throws Exception;

    Review addReview( Review review) throws Exception;

    Review updateReview(Review review, int id) throws Exception;

}