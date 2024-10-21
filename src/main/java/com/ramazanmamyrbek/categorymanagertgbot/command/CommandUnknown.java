package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * CommandUnknown class handles any unrecognized commands entered by the user.
 *
 * This class implements the {@link Command} interface and provides feedback to the user
 * when they input a command that is not supported or recognized by the bot.
 */
@RequiredArgsConstructor
public class CommandUnknown implements Command {

    private final MessageSender messageSender;

    // Static message sent to the user when they enter an unknown command
    private final String UNKNOWN_MESSAGE = """
          Unknown command. Please try /help to get a list of commands.
          """;

    /**
     * Executes the unknown command handling by sending a message to the user indicating
     * that the command they entered is not recognized and suggests using /help for guidance.
     *
     * @param update The Update object that contains the user's message and chat information.
     */
    @Override
    public void execute(Update update) {
        messageSender.sendMessage(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE);
    }
}

