package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import com.ramazanmamyrbek.categorymanagertgbot.services.CategoryService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandAdd implements Command{
    private final MessageSender messageSender;
    private static final String ADD_MESSAGE = """
            Add element using one of this templates: \n
            1. <parent-element> <child-element>
            2. <element>
            
            Exit executing command by /exit
            """;

    public CommandAdd(MessageSender messageSender) {
        this.messageSender = messageSender;
    }


    @Override
    public void execute(Update update) {
        messageSender.sendMessage(update.getMessage().getChatId().toString(), ADD_MESSAGE);
    }
}
