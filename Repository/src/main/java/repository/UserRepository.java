package repository;

import domain.User;

/**
 * Custom interface for the users repository
 */
public interface UserRepository extends Repository<User>{
    void test();
    User getById(int uid) throws Exception;

}
