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
                int nrInStock = result.getInt("nrInStock");

                Product product = new Product(id, name, price, nrInStock);
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
                    ("SELECT id, name, price, nrInStock FROM UsersProductsFavorites as F INNER JOIN Products as P ON F.pid = P.id WHERE F.uid = ?");
            statement.setInt(1, uid);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                int nrInStock = result.getInt("nrInStock");
                Product product = new Product(id, name, price, nrInStock);
                products.add(product);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting favorites!");
        }
        return products;
    }

    @Override
    public Product getFavoriteByUidAndPid(int uid, int pid) throws Exception {
        Connection con = dbUtils.getConnection();
        Product product = null;
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT id, name, price, nrInStock FROM UsersProductsFavorites as F INNER JOIN Products as P ON F.pid = P.id WHERE F.uid = ? AND F.pid = ?");
            statement.setInt(1, uid);
            statement.setInt(2, pid);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                int nrInStock = result.getInt("nrInStock");
                product = new Product(id, name, price, nrInStock);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting favorite!");
        }
        return product;
    }

    @Override
    public List<Product> getWatchlistByUid(int uid) throws Exception {
        Connection con = dbUtils.getConnection();
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT id, name, price, nrInStock FROM UsersProductsWatchlist as W INNER JOIN Products as P ON W.pid = P.id WHERE W.uid = ?");
            statement.setInt(1, uid);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                int nrInStock = result.getInt("nrInStock");
                Product product = new Product(id, name, price, nrInStock);
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
                    ("SELECT id, name, price, nrInStock FROM UsersProductsWatchlist as W INNER JOIN Products as P ON W.pid = P.id WHERE W.uid = ? AND W.pid = ?");
            statement.setInt(1, uid);
            statement.setInt(2, pid);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                int stock = result.getInt("nrInStock");
                product = new Product(id, name, price, stock);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting watchlist product!");
        }
        return product;
    }

    @Override
    public void addToShoppingCart(int uid, Product p) throws Exception {
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("INSERT INTO UsersProductsShoppingCart (uid, pid) VALUES (?, ?);");
            statement.setInt(1, uid);
            statement.setInt(2, p.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error adding to shopping cart!");
        }
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
    public void deleteFromWatchlist(int uid, int pid) throws Exception {
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("DELETE FROM UsersProductsWatchlist  WHERE uid=? and pid=?;");
            statement.setInt(1, uid);
            statement.setInt(2, pid);

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Error removing from watchlist!");
        }
    }

    @Override
    public void deleteFromFavorites(int uid, int pid) throws Exception {
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("DELETE FROM UsersProductsFavorites WHERE uid=? AND pid=?");
            statement.setInt(1,uid);
            statement.setInt(2,pid);
            //var deleted = getFavoriteByUidAndPid(uid,pid).get(0);
            statement.executeUpdate();
            //return deleted;
        }
        catch (Exception ex) {
            throw new Exception("Error deleting from favorites with pid " + pid + " and uid " + uid);
        }
    }

    @Override
    public void deleteReview(int uid, int pid, int rid) throws Exception {
        Connection con = dbUtils.getConnection();
        con.setAutoCommit(false);
        try {
            PreparedStatement statement1 = con.prepareStatement ("DELETE FROM UsersReviews WHERE userId=? AND reviewId=?");
            statement1.setInt(1,uid);
            statement1.setInt(2,rid);
            statement1.executeUpdate();
            PreparedStatement statement2 = con.prepareStatement ("DELETE FROM ProductsReviews WHERE productId=? AND reviewId=?");
            statement2.setInt(1,pid);
            statement2.setInt(2,rid);
            statement2.executeUpdate();
            PreparedStatement statement3 = con.prepareStatement ("DELETE FROM Reviews WHERE id=?");
            statement3.setInt(1,rid);
            statement3.executeUpdate();

            con.commit();
            con.setAutoCommit(true);
        }
        catch (Exception ex) {
            con.rollback();
            con.setAutoCommit(true);
            throw new Exception("Error deleting from favorites with pid " + pid + " and uid " + uid);
        }
    }

    @Override
    public byte[] getProductImageByPid(int pid) throws Exception
    {
        byte[] res = null;
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement ("SELECT image FROM Products WHERE id=?");
            statement.setInt(1, pid);
            ResultSet result = statement.executeQuery();
            res = result.getBytes("image");
            result.close();
        }
        catch (Exception ex) {
            throw new Exception("Error getting image for product with pid " + pid);
        }
        return res;
    }

    @Override
    public List<Product> getCartProductsByUid(int uid) throws Exception {
        Connection con = dbUtils.getConnection();
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT id, name, price, nrInStock FROM UsersProductsShoppingCart as C INNER JOIN Products as P ON C.pid = P.id WHERE C.uid = ?");
            statement.setInt(1, uid);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                int nrInStock = result.getInt("nrInStock");
                Product product = new Product(id, name, price, nrInStock);
                products.add(product);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting cart items!");
        }
        return products;
    }

    @Override
    public Product getCartProductByUidAndPid(int uid, int pid) throws Exception {
        Connection con = dbUtils.getConnection();
        Product product = null;
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT id, name, price, nrInStock FROM UsersProductsShoppingCart as C INNER JOIN Products as P ON C.pid = P.id WHERE C.uid = ? AND C.pid = ?");
            statement.setInt(1, uid);
            statement.setInt(2, pid);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                int nrInStock = result.getInt("nrInStock");
                product = new Product(id, name, price, nrInStock);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting cart item!");
        }
        return product;
    }



    @Override
    public void deleteCartProductByUidAndPid(int uid, int pid) throws Exception {
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("DELETE FROM UsersProductsShoppingCart WHERE uid=? AND pid=?");
            statement.setInt(1,uid);
            statement.setInt(2,pid);
            //var deleted = getFavoriteByUidAndPid(uid,pid).get(0);
            statement.executeUpdate();
            //return deleted;
        }
        catch (Exception ex) {
            throw new Exception("Error deleting product from cart with pid " + pid + " and uid " + uid);
        }
    }

    @Override
    public void deleteAllCartProducts(int uid) throws Exception {
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("DELETE FROM UsersProductsShoppingCart WHERE uid=?");
            statement.setInt(1,uid);
            statement.executeUpdate();
        }
        catch (Exception ex) {
            throw new Exception("Error deleting all products from cart for user " + uid);
        }
    }

    @Override
    public void removeBoughtProducts(int uid) throws Exception {
        Connection con = dbUtils.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("UPDATE Products " +
                            "SET nrInStock = nrInStock - 1 " +
                            "WHERE EXISTS " +
                            "(SELECT 1 FROM UsersProductsShoppingCart WHERE Products.id = UsersProductsShoppingCart.pid AND UsersProductsShoppingCart.uid = ?) ");
            statement.setInt(1,uid);
            statement.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new Exception("Error removing bought products from db for user " + uid);
        }
    }

    @Override
    public List<Product> getMostRecentBoughtProductsForUser(int uid) throws Exception {
        Connection con = dbUtils.getConnection();
        List<Product> products = new ArrayList<>();

        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT id, name, price, nrInStock from Products" +
                            " WHERE EXISTS " +
                            "(SELECT 1 FROM UsersProductsShoppingCart WHERE Products.id = UsersProductsShoppingCart.pid AND UsersProductsShoppingCart.uid = ?) ");
            statement.setInt(1,uid);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                int nrInStock = result.getInt("nrInStock");
                Product product = new Product(id, name, price, nrInStock);
                products.add(product);
            }
            result.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new Exception("Error getting bought product for user " + uid);
        }
        return products;

    }

    @Override
    public List<Product> getProductsThatBecameOutOfStock(List<Product> boughtProducts) throws Exception {
        Connection con = dbUtils.getConnection();
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT id, name, price, nrInStock FROM Products WHERE nrInStock=? ;");
            statement.setInt(1,0);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                int nrInStock = result.getInt("nrInStock");

                Product product = new Product(id, name, price, nrInStock);
                products.add(product);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting all sales!");
        }

        List <Product> becameOutOfStock = new ArrayList<>();
        for(Product product1 : products)
        {
            for(Product product2: boughtProducts)
            {
                if(product1.getId() == product2.getId())
                {
                    becameOutOfStock.add(product1);
                }
            }
        }
        return becameOutOfStock;
    }

    @Override
    public int size() {
        return -1;
    }
}
