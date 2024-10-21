package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class CommandStart implements Command{
    private final MessageSender messageSender;
    private final String START_MESSAGE = """
          Hello, I am Pandev-telegram-bot-tree. Enter /help command to get help.
          """;

    @Override
    public void execute(Update update) {
        messageSender.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
