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
     * @param jdbcProps properties for database connection
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
    public List<Product> getFavoritesByUid(int uid) throws Exception {
        Connection con = dbUtils.getConnection();
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT * FROM UsersProductsFavorites WHERE uid=?");
            statement.setInt(1, uid);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int pid = result.getInt("pid");
                PreparedStatement statement2 = con.prepareStatement
                        ("SELECT * FROM Products WHERE id=?");
                statement2.setInt(1, pid);

                ResultSet result2 = statement2.executeQuery();
                result2.next();

                int id = result2.getInt("id");
                String name = result2.getString("name");
                int price = result2.getInt("price");
                result2.close();
                Product product = new Product(id, name, price);
                products.add(product);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting favorites!");
        }
        return products;
    }

    @Override
    public List<Product> getFavoriteByUidAndPid(int uid, int pid) throws Exception {
        Connection con = dbUtils.getConnection();
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT id, name, price FROM UsersProductsFavorites as F INNER JOIN Products as P ON F.pid = P.id WHERE F.uid = ? AND F.pid = ?");
            statement.setInt(1, uid);
            statement.setInt(2, pid);

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
            throw new Exception("Error getting favorite!");
        }
        return products;
    }

    @Override
    public List<Product> getWatchlistByUid(int uid) throws Exception {
        Connection con = dbUtils.getConnection();
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT id, name, price FROM UsersProductsWatchlist as W INNER JOIN Products as P ON W.pid = P.id WHERE W.uid = ?");
            statement.setInt(1, uid);

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
            throw new Exception("Error getting watchlist!");
        }
        return products;
    }

    @Override
    public Product getWatchlistByUidAndPid(int uid, int pid) throws Exception {
        Connection con = dbUtils.getConnection();
        Product product = null;
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT id, name, price FROM UsersProductsWatchlist as F INNER JOIN Products as P ON F.pid = P.id WHERE F.uid = ? AND F.pid = ?");
            statement.setInt(1, uid);
            statement.setInt(2, pid);

            ResultSet result = statement.executeQuery();
            int id = result.getInt("id");
            String name = result.getString("name");
            int price = result.getInt("price");
            product = new Product(id, name, price);

            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting favorite!");
        }
        return product;
    }

    @Override
    public void addToFavorites(int uid, Product p) throws Exception {
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("INSERT INTO UsersProductsFavorites (uid, pid) VALUES (?, ?);");
            statement.setInt(1, uid);
            statement.setInt(2, p.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error adding to favorite!");
        }
    }

    @Override
    public void addToWatchlist(int uid, Product p) throws Exception {
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("INSERT INTO UsersProductsWatchlist (uid, pid) VALUES (?, ?);");
            statement.setInt(1, uid);
            statement.setInt(2, p.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error adding to watchlist!");
        }
    }

    @Override
    public int size() {
        return -1;
    }
}
