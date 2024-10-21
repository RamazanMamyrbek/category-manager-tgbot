package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
/**
 * CommandHelp class responsible for displaying a list of available commands to the user.
 *
 * This class implements the {@link Command} interface and provides a help message with
 * all available commands and their descriptions. It is triggered when the user requests help.
 */
@RequiredArgsConstructor
public class CommandHelp implements Command {

    private final MessageSender messageSender;

    // Static message displaying all available commands and their descriptions
    private final String HELP_MESSAGE = """
            Available commands:
                    /start - Start interacting with the bot.
                    /viewTree - Display the tree structure.
                    /addElement - Add an element to the tree.
                    /removeElement - Remove an element from the tree.
                    /clear - Clears the tree.
                    /help - Show this help message.
          """;

    /**
     * Executes the command by sending the help message to the user, listing all available commands.
     *
     * @param update The Update object that contains the user's message and chat information.
     */
    @Override
    public void execute(Update update) {
        messageSender.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
