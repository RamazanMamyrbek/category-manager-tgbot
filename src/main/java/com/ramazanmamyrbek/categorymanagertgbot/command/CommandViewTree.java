package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import com.ramazanmamyrbek.categorymanagertgbot.models.Category;
import com.ramazanmamyrbek.categorymanagertgbot.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


public class CommandViewTree implements Command{
    private final MessageSender messageSender;
    private final CategoryService categoryService;

    public CommandViewTree(MessageSender messageSender, CategoryService categoryService) {
        this.messageSender = messageSender;
        this.categoryService = categoryService;
    }

    @Override
    public void execute(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Here is the current tree structure: \n");

        List<Category> allRootCategories = categoryService.getAllRootCategories();
		allRootCategories.forEach(root -> {
			printCategoryTree(root, 0, stringBuilder);
		});

        stringBuilder.append("\nUse /addElement command to add an element.")
                .append("\nUse /removeElement command to remove an element.")
                .append("\nUse /help command to get a list of commands.");
        messageSender.sendMessage(update.getMessage().getChatId().toString(), stringBuilder.toString());
    }

    private void printCategoryTree(Category category, int level, StringBuilder stringBuilder) {
        stringBuilder
                .append("      ".repeat(level))
                .append("—")
                .append(category.getName())
                .append("\n");
        List<Category> children = category.getChildren();
        if (category.getChildren() != null) {
            for (Category child : children) {
                printCategoryTree(child, level + 1, stringBuilder);
            }
        }
    }
}
