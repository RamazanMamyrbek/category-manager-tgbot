package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * CommandAdd class responsible for handling the addition of elements to the category tree.
 *
 * This class implements the {@link Command} interface and provides functionality to add
 * elements (either parent-child pairs or single elements) to the tree. The command expects
 * input in specific formats and sends a message to guide the user.
 */
public class CommandAdd implements Command {

    private final MessageSender messageSender;

    // Static message to guide the user on how to add elements
    private static final String ADD_MESSAGE = """
            Add element using one of these templates: \n
            1. <parent-element> <child-element>
            2. <element>
            
            Exit executing command by /exit
            """;

    /**
     * Constructor for CommandAdd.
     *
     * @param messageSender The service responsible for sending messages to the Telegram chat.
     */
    public CommandAdd(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    /**
     * Executes the command and sends a message to the user with instructions on how to add elements.
     *
     * @param update The Update object that contains the user's message and chat information.
     */
    @Override
    public void execute(Update update) {
        messageSender.sendMessage(update.getMessage().getChatId().toString(), ADD_MESSAGE);
    }
}
