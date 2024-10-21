package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import com.ramazanmamyrbek.categorymanagertgbot.services.CategoryService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * CommandClear class responsible for clearing the entire category tree.
 *
 * This class implements the {@link Command} interface and provides functionality to remove all
 * elements from the category tree. Once executed, the tree will be completely cleared, and a message
 * will be sent to the user confirming the action.
 */
public class CommandClear implements Command {

    private final MessageSender messageSender;
    private final CategoryService categoryService;

    // Static message confirming that the tree has been cleared
    private static final String CLEAR_MESSAGE = """
            Tree was cleared.
            Use /help command to get a list of commands.
            """;

    /**
     * Constructor for CommandClear.
     *
     * @param messageSender   The service responsible for sending messages to the Telegram chat.
     * @param categoryService The service responsible for managing the category tree.
     */
    public CommandClear(MessageSender messageSender, CategoryService categoryService) {
        this.messageSender = messageSender;
        this.categoryService = categoryService;
    }

    /**
     * Executes the command by clearing the category tree and sending a confirmation message to the user.
     *
     * @param update The Update object that contains the user's message and chat information.
     */
    @Override
    public void execute(Update update) {
        categoryService.clearTree();
        messageSender.sendMessage(update.getMessage().getChatId().toString(), CLEAR_MESSAGE);
    }
}

