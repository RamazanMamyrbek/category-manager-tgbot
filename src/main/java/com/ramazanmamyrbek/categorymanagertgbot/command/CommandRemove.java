package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * CommandRemove class responsible for handling the removal of elements from the category tree.
 *
 * This class implements the {@link Command} interface and provides functionality to remove an
 * element from the category tree. The user is prompted to enter the name of the element they
 * wish to remove, and the command can be exited using the /exit command.
 */
public class CommandRemove implements Command {

    private final MessageSender messageSender;

    // Static message prompting the user to enter the element's name to be removed
    private static final String REMOVE_MESSAGE = """
            Enter element name: 
            
            Exit executing command by /exit
            """;

    /**
     * Constructs a new CommandRemove instance with the given MessageSender.
     *
     * @param messageSender An instance of {@link MessageSender} used to send messages to the user.
     */
    public CommandRemove(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    /**
     * Executes the remove command by sending a message that prompts the user to input the name
     * of the element they want to remove. The user can also exit the command by typing /exit.
     *
     * @param update The Update object that contains the user's message and chat information.
     */
    @Override
    public void execute(Update update) {
        messageSender.sendMessage(update.getMessage().getChatId().toString(), REMOVE_MESSAGE);
    }
}

