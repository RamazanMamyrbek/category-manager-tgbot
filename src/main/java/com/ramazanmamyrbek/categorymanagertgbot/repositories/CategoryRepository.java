package com.ramazanmamyrbek.categorymanagertgbot.repositories;

import com.ramazanmamyrbek.categorymanagertgbot.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByParentIsNull();
    Optional<Category> findByName(String name);
    @Query("select c from Category c where c.parent.name = :word")
    List<Category> findAllByParentName(String word);

    @Query("delete from Category c where c.id = :id")
    void removeById(Long id);
}
