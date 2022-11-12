package repository;

import domain.Product;
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
 * Database containing products
 */
@Component
public class ProductRepositoryDatabase implements ProductRepository {
    private JdbcUtils dbUtils;

    /**
     * Constructor for repository using Spring beans
     * @param props properties for database connection
     */
    public ProductRepositoryDatabase(Properties jdbcProps) {
        dbUtils = new JdbcUtils(jdbcProps);
    }

    @Override
    public void test() {
        System.out.println("Consider yourself tested");
    }

    @Override
    public Product add(Product e) {
        return null;
    }

    @Override
    public Product delete(Product e) {
        return null;
    }

    @Override
    public Product find(Product e) {
        return null;
    }

    @Override
    public Product update(Product e) {
        return null;
    }

    @Override
    public List<Product> getAll() throws Exception {
        Connection con = dbUtils.getConnection();
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT * FROM Products");

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int price = result.getInt("price");

                Product product = new Product(id, name, price);
                products.add(product);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting all sales!");
        }
        return products;
    }

    @Override
    public int size() {
        return -1;
    }
}
