package rest_service;

import domain.LoginForm;
import domain.Product;
import domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.ProductRepository;
import repository.ReviewRepository;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.ProductRepository;
import repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/colectiv/")
public class RestControl {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @RequestMapping(value="/products/", method= RequestMethod.GET)
    public Product[] getAll() throws Exception {
        return productRepository.getAll().toArray(new Product[0]);
    }

    @RequestMapping(value="/{uid}/favorites", method= RequestMethod.GET)
    public Product[] getFavoritesForUser(@PathVariable("uid") int uid) throws Exception {
        return productRepository.getFavoritesByUid(uid).toArray(new Product[0]);
    }

    @RequestMapping(value="/{uid}/watchlist", method= RequestMethod.GET)
    public Product[] getWatchlistForUser(@PathVariable("uid") int uid) throws Exception {
        return productRepository.getWatchlistByUid(uid).toArray(new Product[0]);
    }

    @RequestMapping(value="/{uid}/favorites/{pid}", method= RequestMethod.GET)
    public Product[] getFavoritesForUser(@PathVariable("uid") int uid, @PathVariable("pid") int pid) throws Exception {
        return productRepository.getFavoriteByUidAndPid(uid, pid).toArray(new Product[0]);
    }

    @RequestMapping(value="/{uid}/watchlist/{pid}", method= RequestMethod.GET)
    public int getWatchlistProductForUser(@PathVariable("uid") int uid, @PathVariable("pid") int pid) throws Exception {
        if(productRepository.getWatchlistByUidAndPid(uid, pid) != null) return 1;
        else return 0;
    }

   //GET all reviews for a product
    @RequestMapping(value="/product/{pid}/reviews", method= RequestMethod.GET)
    public Review[] getReviewsForProduct(@PathVariable("pid") int pid) throws Exception {
        return reviewRepository.getReviewsByProduct(pid).toArray(new Review[0]);
    }

    //GET the reviews average for a specific product
    @RequestMapping(value="/product/{pid}/reviewsAverage", method= RequestMethod.GET)
    public float getReviewsAverageForProduct(@PathVariable("pid") int pid) throws Exception {
        float sum = 0;
        Review[] reviews = reviewRepository.getReviewsByProduct(pid).toArray(new Review[0]);

        for(Review review : reviews)
        {
            sum += review.getNrOfStars();
        }

        return sum / reviews.length;
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

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public User[] getUsers() throws Exception{
        return userRepository.getAll().toArray(new User[0]);
    }

    //ADD
    @RequestMapping(value="/users", method = RequestMethod.POST)
    public User create(@RequestBody User user) throws Exception {
        System.out.println("Adding user ... ");
        return userRepository.add(user);
    }

    @RequestMapping(value="/{uid}/favorites", method= RequestMethod.POST)
    public void addToFavorites(@PathVariable("uid") int uid, @RequestBody Product product) throws Exception {
        productRepository.addToFavorites(uid, product);
    }

    @RequestMapping(value="/{uid}/watchlist", method= RequestMethod.POST)
    public void addToWatchlist(@PathVariable("uid") int uid, @RequestBody Product product) throws Exception {
        productRepository.addToWatchlist(uid, product);
    }

    // TEMPORARY: this method only checks if the user credentials provided in the POST request are valid
    // returns 1 if user exists and 0 if not
    @RequestMapping(value="/login", method= RequestMethod.POST)
    public int login(@RequestBody LoginForm loginForm) {
        User temp = new User(null, loginForm.getEmail(), loginForm.getPassword());
        User res = userRepository.find(temp);
        if(res == null) return -1;
        else return res.getId();
    }
}
