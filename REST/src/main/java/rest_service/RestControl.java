package rest_service;

import domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
}
