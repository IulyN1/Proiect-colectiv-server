package rest_service;

import domain.Product;
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

    @RequestMapping(value="/products/", method= RequestMethod.GET)
    public Product[] getAll() throws Exception {
        return productRepository.getAll().toArray(new Product[0]);
    }

    @RequestMapping(value="/{uid}/favorites", method= RequestMethod.GET)
    public Product[] getFavoritesForUser(@PathVariable("uid") int uid) throws Exception {
        return productRepository.getFavoritesByUid(uid).toArray(new Product[0]);
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
}
