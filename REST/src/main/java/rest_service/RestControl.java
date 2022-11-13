package rest_service;

import domain.Product;
import domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.ProductRepository;
import repository.ReviewRepository;

@CrossOrigin
@RestController
@RequestMapping("/colectiv/")
public class RestControl {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @RequestMapping(value="/products/", method= RequestMethod.GET)
    public Product[] getAll() throws Exception {
        return productRepository.getAll().toArray(new Product[0]);
    }

    @RequestMapping(value="/product/{pid}/reviews", method= RequestMethod.GET)
    public Review[] getReviewsForProduct(@PathVariable("pid") int pid) throws Exception {
        return reviewRepository.getReviewsByProduct(pid).toArray(new Review[0]);
    }

    @RequestMapping(value="/{uid}/favorites", method= RequestMethod.GET)
    public Product[] getFavoritesForUser(@PathVariable("uid") int uid) throws Exception {
        return productRepository.getFavoritesByUid(uid).toArray(new Product[0]);
    }

    //ADD
    @RequestMapping(value="/reviews", method = RequestMethod.POST)
    public Review create(@RequestBody Review review) throws Exception{
        System.out.println("Adding review ... ");
        Review r=reviewRepository.addReview(review);
        return r;

    }
    //UPDATE
    @RequestMapping(value = "/reviews/{id}", method = RequestMethod.PUT)
    public Review update(@PathVariable("id") int id, @RequestBody Review review) throws Exception {
        System.out.println("Updating review ...");
        return reviewRepository.updateReview(review, id);

    }

}
