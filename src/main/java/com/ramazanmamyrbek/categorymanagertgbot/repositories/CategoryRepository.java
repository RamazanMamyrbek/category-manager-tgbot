package com.ramazanmamyrbek.categorymanagertgbot.repositories;

import com.ramazanmamyrbek.categorymanagertgbot.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Category} entities.
 * Provides methods for CRUD operations and custom queries related to categories.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Retrieves all categories that do not have a parent (root categories).
     *
     * @return a list of root categories.
     */
    List<Category> findAllByParentIsNull();

    /**
     * Finds a category by its unique name.
     *
     * @param name the name of the category to search for.
     * @return an {@link Optional} containing the category if found, otherwise empty.
     */
    Optional<Category> findByName(String name);

    /**
     * Retrieves all categories whose parent category has the specified name.
     *
     * @param word the name of the parent category.
     * @return a list of categories that are children of the specified parent category.
     */
    @Query("select c from Category c where c.parent.name = :word")
    List<Category> findAllByParentName(String word);

    /**
     * Deletes a category by its unique name.
     *
     * @param name the name of the category to be deleted.
     */
    void deleteByName(String name);
}
