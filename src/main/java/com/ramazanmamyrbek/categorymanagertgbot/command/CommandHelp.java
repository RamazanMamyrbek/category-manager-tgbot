package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class CommandHelp implements Command{
    private final MessageSender messageSender;
    private final String HELP_MESSAGE = """
            Available commands:
                    /start - Start interacting with the bot.
                    /viewTree - Display the tree structure.
                    /addElement - Add an element to the tree.
                    /removeElement - Remove an element from the tree.
                    /clear - Clears the tree.
                    /help - Show this help message.
          """;

    @Override
    public void execute(Update update) {
        messageSender.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
