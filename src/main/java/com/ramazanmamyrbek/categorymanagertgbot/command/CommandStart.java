package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * CommandStart class responsible for handling the initial interaction with the bot.
 *
 * This class implements the {@link Command} interface and sends a welcome message to
 * the user when they first start interacting with the bot. It also provides guidance
 * on how to access help using the /help command.
 */
@RequiredArgsConstructor
public class CommandStart implements Command {

    private final MessageSender messageSender;

    // Static message sent to the user when they start interacting with the bot
    private final String START_MESSAGE = """
          Hello, I am Pandev-telegram-bot-tree. Enter /help command to get help.
          """;

    /**
     * Executes the start command by sending a welcome message to the user, introducing the bot
     * and providing instructions to use the /help command for further assistance.
     *
     * @param update The Update object that contains the user's message and chat information.
     */
    @Override
    public void execute(Update update) {
        messageSender.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}

