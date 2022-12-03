package repository;

import domain.Product;

import java.util.List;

/**
 * Custom interface for the product repository
 */
public interface ProductRepository extends Repository<Product>{
    void test();

    List<Product> getFavoritesByUid(int uid) throws Exception;

    List<Product> getFavoriteByUidAndPid(int uid, int pid) throws Exception;

    void addToFavorites(int uid, Product p) throws Exception;
}
