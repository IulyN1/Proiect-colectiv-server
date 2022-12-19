package repository;

import domain.Product;

import java.util.List;

/**
 * Custom interface for the product repository
 */
public interface ProductRepository extends Repository<Product>{
    List<Product> getFavoritesByUid(int uid) throws Exception;

    List<Product> getWatchlistByUid(int uid) throws Exception;

    Product getFavoriteByUidAndPid(int uid, int pid) throws Exception;

    Product getWatchlistByUidAndPid(int uid, int pid) throws Exception;

    void addToFavorites(int uid, Product p) throws Exception;

    void addToWatchlist(int uid, Product p) throws Exception;

    void deleteFromFavorites(int uid, int id) throws Exception;

    void deleteReview(int uid, int pid, int rid) throws Exception;

    byte[] getProductImageByPid(int pid) throws Exception;
}

