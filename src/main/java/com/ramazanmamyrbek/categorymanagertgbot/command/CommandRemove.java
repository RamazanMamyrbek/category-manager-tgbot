package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandRemove implements Command{
    private final MessageSender messageSender;
    private static final String ADD_MESSAGE = """
            Enter element name: 
            
            Exit executing command by /exit
            """;

    public CommandRemove(MessageSender messageSender) {
        this.messageSender = messageSender;
    }


    @Override
    public void execute(Update update) {
        messageSender.sendMessage(update.getMessage().getChatId().toString(), ADD_MESSAGE);
    }
}
