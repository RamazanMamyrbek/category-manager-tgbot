package com.ramazanmamyrbek.categorymanagertgbot.controller;

import com.ramazanmamyrbek.categorymanagertgbot.command.*;
import com.ramazanmamyrbek.categorymanagertgbot.services.CategoryService;
import com.ramazanmamyrbek.categorymanagertgbot.utils.CommandNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

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

    public void register(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        this.messageSender = new MessageSender(telegramBot);
        this.categoryService.setMessageSender(messageSender);
        this.categoryService.setUpdateController(this);
    }
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

    private void sendText(Update update) {
        String messageCame = update.getMessage().getText();
        messageSender.sendMessage(update.getMessage().getChatId().toString(), messageCame);
    }

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

    public void setAddStatus(Boolean addStatus) {
        this.addStatus = addStatus;
    }

    public void setRemoveStatus(Boolean removeStatus) {
        this.removeStatus = removeStatus;
    }
}
