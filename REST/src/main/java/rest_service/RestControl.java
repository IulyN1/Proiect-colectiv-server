package rest_service;

import domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.ProductRepository;

@CrossOrigin
@RestController
@RequestMapping("/colectiv/")
public class RestControl {
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value="/products/", method= RequestMethod.GET)
    public Product[] getAll() throws Exception {
        return productRepository.getAll().toArray(new Product[0]);
    }

    @RequestMapping(value="/{uid}/favorites", method= RequestMethod.GET)
    public Product[] getFavoritesForUser(@PathVariable("uid") int uid) throws Exception {
        return productRepository.getFavoritesByUid(uid).toArray(new Product[0]);
    }

    @RequestMapping(value="/{uid}/favorites", method= RequestMethod.POST)
    public void addToFavorites(@PathVariable("uid") int uid, @RequestBody Product product) throws Exception {
        productRepository.addToFavorites(uid, product);
    }
}
