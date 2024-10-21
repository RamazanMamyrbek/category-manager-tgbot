package com.ramazanmamyrbek.categorymanagertgbot.controller;

import com.ramazanmamyrbek.categorymanagertgbot.command.*;
import com.ramazanmamyrbek.categorymanagertgbot.services.CategoryService;
import com.ramazanmamyrbek.categorymanagertgbot.utils.CommandNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * UpdateController handles incoming updates from the Telegram bot,
 * processes user commands, and manages the state for adding or removing categories.
 *
 * It coordinates between the TelegramBot and CategoryService, facilitating
 * the execution of commands based on user input.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateController {
    private MessageSender messageSender;
    private TelegramBot telegramBot;
    private Update update;
    private final CategoryService categoryService;
    private Boolean addStatus = false;
    private Boolean removeStatus = false;

    /**
     * Registers the TelegramBot and initializes the MessageSender and CategoryService.
     *
     * @param telegramBot The instance of the TelegramBot to register.
     */
    public void register(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        this.messageSender = new MessageSender(telegramBot);
        this.categoryService.setMessageSender(messageSender);
        this.categoryService.setUpdateController(this);
    }

    /**
     * Processes incoming updates from the Telegram bot.
     * Determines if the update is a command or if it relates to adding/removing categories.
     *
     * @param update The update received from Telegram.
     */
    public void processUpdate(Update update) {
        this.update = update;
        if(update.getMessage() == null || update.getMessage().getText() == null) {
            return;
        }
        if(addStatus) {
            String messageCame = update.getMessage().getText().trim();
            if(messageCame.equals(CommandNames.EXIT)) {
                addStatus = false;
                update.getMessage().setText("/help");
                sendCommand(update);
            } else {
                categoryService.addCategory(messageCame, update);
            }
        } else if (removeStatus) {
            String messageCame = update.getMessage().getText().trim();
            if(messageCame.equals(CommandNames.EXIT)) {
                removeStatus = false;
                update.getMessage().setText("/help");
                sendCommand(update);
            } else {
                categoryService.removeCategory(messageCame, update);
            }
        } else {
            sendCommand(update);
        }
    }

    /**
     * Sends a text message back to the user.
     *
     * @param update The update containing the message to be sent.
     */
    private void sendText(Update update) {
        String messageCame = update.getMessage().getText();
        messageSender.sendMessage(update.getMessage().getChatId().toString(), messageCame);
    }

    /**
     * Executes the command based on the user's input.
     * This method interprets the command and delegates execution to the appropriate Command class.
     *
     * @param update The update containing the command to execute.
     */
    private void sendCommand(Update update) {
        String messageCame = update.getMessage().getText().split(" ")[0].trim();
        switch (messageCame) {
            case CommandNames.START -> new CommandStart(messageSender).execute(update);
            case CommandNames.HELP -> new CommandHelp(messageSender).execute(update);
            case CommandNames.VIEW_TREE -> new CommandViewTree(messageSender, categoryService).execute(update);
            case CommandNames.ADD_ELEMENT -> {
                new CommandAdd(messageSender).execute(update);
                addStatus = true;
            }
            case CommandNames.REMOVE_ELEMENT -> {
                new CommandRemove(messageSender).execute(update);
                removeStatus = true;
            }
            case CommandNames.CLEAR -> new CommandClear(messageSender, categoryService).execute(update);
            default -> new CommandUnknown(messageSender).execute(update);
        }
    }

    /**
     * Sets the status for adding categories.
     *
     * @param addStatus The status indicating whether the bot is in adding mode.
     */
    public void setAddStatus(Boolean addStatus) {
        this.addStatus = addStatus;
    }

    /**
     * Sets the status for removing categories.
     *
     * @param removeStatus The status indicating whether the bot is in removing mode.
     */
    public void setRemoveStatus(Boolean removeStatus) {
        this.removeStatus = removeStatus;
    }
}
