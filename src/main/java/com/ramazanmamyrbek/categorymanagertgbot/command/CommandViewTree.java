package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import com.ramazanmamyrbek.categorymanagertgbot.models.Category;
import com.ramazanmamyrbek.categorymanagertgbot.services.CategoryService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

/**
 * CommandViewTree class is responsible for displaying the current tree structure of categories.
 *
 * This class implements the {@link Command} interface and retrieves the hierarchical structure
 * of categories from the {@link CategoryService}. It formats and sends a message to the user
 * with the visual representation of the category tree.
 */
public class CommandViewTree implements Command {

    private final MessageSender messageSender;
    private final CategoryService categoryService;

    /**
     * Constructs a CommandViewTree instance with the specified MessageSender and CategoryService.
     *
     * @param messageSender The service responsible for sending messages to the user.
     * @param categoryService The service responsible for managing categories.
     */
    public CommandViewTree(MessageSender messageSender, CategoryService categoryService) {
        this.messageSender = messageSender;
        this.categoryService = categoryService;
    }

    /**
     * Executes the command to display the current tree structure of categories.
     * It retrieves all root categories, formats them into a string, and sends the
     * formatted message to the user.
     *
     * @param update The Update object that contains the user's message and chat information.
     */
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

    /**
     * Recursively formats and appends the category tree structure to the provided StringBuilder.
     *
     * @param category The current category to process.
     * @param level The depth level of the category in the tree, used for indentation.
     * @param stringBuilder The StringBuilder to which the formatted category names are appended.
     */
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
