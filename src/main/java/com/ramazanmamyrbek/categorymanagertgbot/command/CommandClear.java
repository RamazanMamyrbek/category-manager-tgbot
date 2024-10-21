package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import com.ramazanmamyrbek.categorymanagertgbot.services.CategoryService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandClear implements Command{
    private final MessageSender messageSender;
    private final CategoryService categoryService;
    private static final String CLEAR_MESSAGE = """
            Tree was cleared.
            Use /help command to get a list of commands.
            """;

    public CommandClear(MessageSender messageSender, CategoryService categoryService) {
        this.messageSender = messageSender;
        this.categoryService = categoryService;
    }

    @Override
    public void execute(Update update) {
        categoryService.clearTree();
        messageSender.sendMessage(update.getMessage().getChatId().toString(), CLEAR_MESSAGE);
    }
}
