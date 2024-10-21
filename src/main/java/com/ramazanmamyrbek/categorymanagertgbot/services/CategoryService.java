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

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private MessageSender messageSender;
    private UpdateController updateController;

    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getAllRootCategories() {
        return categoryRepository.findAllByParentIsNull();
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new CategoryNotFoundException("""
                Category with name '%s' is not found.
                """.formatted(name)));
    }

    @Transactional
    public void addCategory(String messageCame, Update update) {
        String[] words = messageCame.split(" ");
        if(messageCame.startsWith("/")) {
            messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                    Invalid word.
                    
                    Please, Add element using one of this templates: \n
                    1. <parent-element> <child-element>
                    2. <element>
                                
                    Exit executing command by /exit
                    """);
            return;
        }
        if(words.length == 1) {
            if(categoryRepository.findByName(words[0]).isPresent()) {
                messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                        Element with name '%s' is already exists. Try another name.
                        
                        Add element using one of this templates: \n
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
            if(!categoryOptional.isPresent()) {
                messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                    Element with name '%s' is not found. Try again.
                    
                    Add element using one of this templates: \n
                    1. <parent-element> <child-element>
                    2. <element>
                                
                    Exit executing command by /exit
                    """.formatted(words[0]));
                return;
            }
            if(categoryRepository.findByName(words[1]).isPresent()) {
                messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                        Element with name '%s' is already exists. Try another name.
                        
                        Add element using one of this templates: \n
                        1. <parent-element> <child-element>
                        2. <element>
                                    
                        Exit executing command by /exit
                        """.formatted(words[1]));
                return;
            }
            List<Category> childCategories = categoryRepository.findAllByParentName(words[0]);
            List<String> childCategoriesNames = childCategories.stream().map(Category::getName).toList();
            if(childCategoriesNames.contains(words[1])) {
                messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                        Element with name '%s' is already exists for parent '%s'. Try another name.
                        
                        Add element using one of this templates: \n
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
        } else  {
            messageSender.sendMessage(update.getMessage().getChatId().toString(), """
                    Invalid word.
                    
                    Please, Add element using one of this templates: \n
                    1. <parent-element> <child-element>
                    2. <element>
                                
                    Exit executing command by /exit
                    """);
        }
    }
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void setUpdateController(UpdateController updateController) {
        this.updateController = updateController;
    }

    @Transactional
    public void removeCategory(String messageCame, Update update) {
        try {
            if(messageCame.contains(" ")) {
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

    public void removeCategoryByName(String name) {
        Category category = getCategoryByName(name);
        categoryRepository.deleteByName(name);
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category is not found"));
    }

    @Transactional
    public void clearTree() {
        categoryRepository.deleteAll();
    }
}
