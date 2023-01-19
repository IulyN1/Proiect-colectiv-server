package rest_service;

import domain.LoginForm;
import domain.Product;
import domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.ProductRepository;
import repository.ReviewRepository;
import domain.User;
import repository.UserRepository;
import java.util.Base64;


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
    public Product[] getFavoritesForUser(@PathVariable("uid") int uid
    ) throws Exception {
        return productRepository.getFavoritesByUid(uid).toArray(new Product[0]);
    }

    @RequestMapping(value="/{uid}/cart", method= RequestMethod.GET)
    public Product[] getCartForUser(@PathVariable("uid") int uid
    ) throws Exception {
        return productRepository.getCartProductsByUid(uid).toArray(new Product[0]);
    }

    @RequestMapping(value="/{uid}/watchlist", method= RequestMethod.GET)
    public Product[] getWatchlistForUser(@PathVariable("uid") int uid) throws Exception {
        return productRepository.getWatchlistByUid(uid).toArray(new Product[0]);
    }

    @RequestMapping(value="/{uid}/favorites/{pid}", method= RequestMethod.GET)
    public Product getFavoriteProductForUser(@PathVariable("uid") int uid, @PathVariable("pid") int pid) throws Exception {
        return productRepository.getFavoriteByUidAndPid(uid, pid);
    }

    @RequestMapping(value="/{uid}/cart/{pid}", method= RequestMethod.GET)
    public Product getCartProductForUser(@PathVariable("uid") int uid, @PathVariable("pid") int pid) throws Exception {
        return productRepository.getCartProductByUidAndPid(uid, pid);
    }

    @RequestMapping(value="/{uid}/watchlist/{pid}", method= RequestMethod.GET)
    public Product getWatchlistProductForUser(@PathVariable("uid") int uid, @PathVariable("pid") int pid) throws Exception {
        return productRepository.getWatchlistByUidAndPid(uid, pid);
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
    public User create(@RequestBody User user) {
        System.out.println("Adding user ... ");
        return userRepository.add(user);
    }

    @RequestMapping(value="/{uid}/shoppingcart", method= RequestMethod.POST)
    public void addToShoppingCart(@PathVariable("uid") int uid, @RequestBody Product product) throws Exception {
        productRepository.addToShoppingCart(uid, product);
    }

    @RequestMapping(value="/{uid}/favorites", method= RequestMethod.POST)
    public void addToFavorites(@PathVariable("uid") int uid, @RequestBody Product product) throws Exception {
        productRepository.addToFavorites(uid, product);
    }

    @RequestMapping(value="/{uid}/watchlist", method= RequestMethod.POST)
    public void addToWatchlist(@PathVariable("uid") int uid, @RequestBody Product product) throws Exception {
        productRepository.addToWatchlist(uid, product);
    }

    //DELETE a product from the watchlist of a user
    @RequestMapping(value="/{uid}/watchlist/{pid}", method= RequestMethod.DELETE)
    public void removeFromWatchlist(@PathVariable("uid") int uid, @PathVariable("pid") int pid) throws Exception {
        productRepository.deleteFromWatchlist(uid, pid);
    }

    // DELETE
    @RequestMapping(value="{uid}/favorites/{pid}", method = RequestMethod.DELETE)
    public void deleteFromFavorites(@PathVariable("uid") int uid, @PathVariable("pid") int pid) throws Exception {
        System.out.println("Deleted favorite...");
        productRepository.deleteFromFavorites(uid, pid);
    }

    @RequestMapping(value = "/reviews/{rid}", method = RequestMethod.DELETE)
    public void deleteReview(@PathVariable("rid") int rid, @RequestBody Review review) throws Exception {
        System.out.println("Deleted review...");
        productRepository.deleteReview(review.getUserId(), review.getProductId(), rid);
    }

    @RequestMapping(value="/{uid}/cart/{pid}", method = RequestMethod.DELETE)
    public void deleteProductFromCart(@PathVariable("uid") int uid, @PathVariable("pid") int pid) throws Exception {
        System.out.println("Deleting product from user's cart...");
        productRepository.deleteCartProductByUidAndPid(uid, pid);
    }

    @RequestMapping(value="/{uid}/cart", method = RequestMethod.DELETE)
    public void buyAndDeleteAllProductsFromCart(@PathVariable("uid") int uid) throws Exception {
        System.out.println("Removing bought products from storage...");
        productRepository.removeBoughtProducts(uid);
        System.out.println("Deleting products from user's cart...");
        productRepository.deleteAllCartProducts(uid);
    }

    // TEMPORARY: this method only checks if the user credentials provided in the POST request are valid
    // returns 1 if user exists and 0 if not
    // get image for product, returns encoded in base64 byte array or null if pid doesn't exist
    @RequestMapping(value="/product/{pid}/image", method= RequestMethod.GET)
    public byte[] getImageForProduct(@PathVariable("pid") int pid) throws Exception {
        return Base64.getEncoder().encodeToString(productRepository.getProductImageByPid(pid)).getBytes();
    }

    // returns userId if user exists or -1 if not
    @RequestMapping(value="/login", method= RequestMethod.POST)
    public int login(@RequestBody LoginForm loginForm) {
        User temp = new User(null, loginForm.getEmail(), loginForm.getPassword());
        User res = userRepository.find(temp);
        if(res == null) return -1;
        else return res.getId();
    }
}
