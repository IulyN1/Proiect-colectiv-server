package repository;

import java.util.List;

/**
 * Base repository interface
 * Consider extending it with another interface if you want to add more functions.
 * @param <T> datatype to store
 */
public interface Repository<T> {
    /**
     * Adds an entity
     * @param e entity
     * @return the entity if it was added or the already existent entity if it already exists
     */
    T add(T e);

    /**
     * Deletes an entity
     * @param e entity
     * @return the deleted entity
     */
    T delete(T e);

    /**
     * Finds an entity
     * @param e entity
     * @return the entity
     */
    T find (T e);

    /**
     * Updates an entity
     * @param e entity with updated fields
     * @return the old entity
     */
    T update(T e);

    /**
     * Gets all the entities in the repository
     * @return list of all the entities
     */
    List<T> getAll() throws Exception;

    /**
     * Returns the number of entities in the repository
     * @return int
     */
    int size();
}
