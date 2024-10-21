package com.ramazanmamyrbek.categorymanagertgbot.services;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import com.ramazanmamyrbek.categorymanagertgbot.controller.UpdateController;
import com.ramazanmamyrbek.categorymanagertgbot.exception.CategoryNameContainsWhiteSpaceException;
import com.ramazanmamyrbek.categorymanagertgbot.exception.CategoryNotFoundException;
import com.ramazanmamyrbek.categorymanagertgbot.models.Category;
import com.ramazanmamyrbek.categorymanagertgbot.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link Category} entities.
 * Provides methods for adding, removing, and retrieving categories,
 * as well as clearing the category tree.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private MessageSender messageSender;
    private UpdateController updateController;

    /**
     * Saves the given category to the repository.
     *
     * @param category the category to save.
     */
    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }

    /**
     * Retrieves all categories from the repository.
     *
     * @return a list of all categories.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Retrieves all root categories (categories without a parent).
     *
     * @return a list of root categories.
     */
    public List<Category> getAllRootCategories() {
        return categoryRepository.findAllByParentIsNull();
    }

    /**
     * Retrieves a category by its name.
     *
     * @param name the name of the category to search for.
     * @return the found category.
     * @throws CategoryNotFoundException if the category is not found.
     */
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("""
                        Category with name '%s' is not found.
                        """.formatted(name)));
    }

    /**
     * Adds a new category based on the provided message.
     * Supports adding both root and child categories.
     *
     * @param messageCame the message containing the category information.
     * @param update      the update containing chat information.
     */
    @Transactional
    public void addCategory(String messageCame, Update update) {
        String[] words = messageCame.split(" ");
        if (messageCame.startsWith("/")) {
            messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                    Invalid word.
                    
                    Please, Add element using one of these templates: \n
                    1. <parent-element> <child-element>
                    2. <element>
                                
                    Exit executing command by /exit
                    """);
            return;
        }

        if (words.length == 1) {
            if (categoryRepository.findByName(words[0]).isPresent()) {
                messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                        Element with name '%s' already exists. Try another name.
                        
                        Add element using one of these templates: \n
                        1. <parent-element> <child-element>
                        2. <element>
                                    
                        Exit executing command by /exit
                        """.formatted(words[0]));
                return;
            }
            Category category = Category.builder()
                    .name(words[0])
                    .parent(null)
                    .build();
            categoryRepository.save(category);
            updateController.setAddStatus(false);
            messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                    Element has been successfully added.
                    
                    Use /viewTree to display the tree.
                    Use /help command to get a list of commands.
                    """);
        } else if (words.length == 2) {
            Optional<Category> categoryOptional = categoryRepository.findByName(words[0]);
            if (!categoryOptional.isPresent()) {
                messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                    Element with name '%s' is not found. Try again.
                    
                    Add element using one of these templates: \n
                    1. <parent-element> <child-element>
                    2. <element>
                                
                    Exit executing command by /exit
                    """.formatted(words[0]));
                return;
            }
            if (categoryRepository.findByName(words[1]).isPresent()) {
                messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                        Element with name '%s' already exists. Try another name.
                        
                        Add element using one of these templates: \n
                        1. <parent-element> <child-element>
                        2. <element>
                                    
                        Exit executing command by /exit
                        """.formatted(words[1]));
                return;
            }
            List<Category> childCategories = categoryRepository.findAllByParentName(words[0]);
            List<String> childCategoriesNames = childCategories.stream().map(Category::getName).toList();
            if (childCategoriesNames.contains(words[1])) {
                messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                        Element with name '%s' already exists for parent '%s'. Try another name.
                        
                        Add element using one of these templates: \n
                        1. <parent-element> <child-element>
                        2. <element>
                                    
                        Exit executing command by /exit
                        """.formatted(words[1], words[0]));
                return;
            }
            Category category = Category.builder()
                    .name(words[1])
                    .parent(categoryOptional.get())
                    .build();
            categoryRepository.save(category);
            updateController.setAddStatus(false);
            messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                    Element has been successfully added.
                    
                    Use /viewTree to display the tree.
                    Use /help command to get a list of commands.
                    """);
        } else {
            messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                    Invalid word.
                    
                    Please, Add element using one of these templates: \n
                    1. <parent-element> <child-element>
                    2. <element>
                                
                    Exit executing command by /exit
                    """);
        }
    }

    /**
     * Sets the message sender for the service.
     *
     * @param messageSender the message sender to set.
     */
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    /**
     * Sets the update controller for the service.
     *
     * @param updateController the update controller to set.
     */
    public void setUpdateController(UpdateController updateController) {
        this.updateController = updateController;
    }

    /**
     * Removes a category based on the provided message.
     *
     * @param messageCame the message containing the category name to remove.
     * @param update      the update containing chat information.
     */
    @Transactional
    public void removeCategory(String messageCame, Update update) {
        try {
            if (messageCame.contains(" ")) {
                throw new CategoryNameContainsWhiteSpaceException("Enter only one word");
            }
            messageCame = messageCame.trim();
            removeCategoryByName(messageCame);
            messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                    Element has been successfully removed.
                    
                    Use /viewTree to display the tree.
                    Use /help command to get a list of commands.
                    """);
            updateController.setRemoveStatus(false);

        } catch (CategoryNameContainsWhiteSpaceException e) {
            messageSender.sendMessage(update.getMessage().getChatId().toString(), e.getMessage());
        } catch (CategoryNotFoundException e) {
            messageSender.sendMessage(update.getMessage().getChatId().toString(), e.getMessage());
        }
    }

    /**
     * Removes a category by its name.
     *
     * @param name the name of the category to remove.
     */
    public void removeCategoryByName(String name) {
        Category category = getCategoryByName(name);
        categoryRepository.deleteByName(name);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to retrieve.
     * @return the found category.
     * @throws CategoryNotFoundException if the category is not found.
     */
    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category is not found"));
    }

    /**
     * Clears the entire category tree by deleting all categories.
     */
    @Transactional
    public void clearTree() {
        categoryRepository.deleteAll();
    }
}
