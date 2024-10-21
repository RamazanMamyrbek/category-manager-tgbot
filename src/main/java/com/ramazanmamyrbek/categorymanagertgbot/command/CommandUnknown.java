package com.ramazanmamyrbek.categorymanagertgbot.command;

import com.ramazanmamyrbek.categorymanagertgbot.controller.MessageSender;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class CommandUnknown implements Command {
    private final MessageSender messageSender;
    private final String UNKNOWN_MESSAGE = """
          Unknown command. Please try /help to get a list of commands.
          """;

    @Override
    public void execute(Update update) {
        messageSender.sendMessage(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE);
    }
}
