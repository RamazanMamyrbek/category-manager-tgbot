package com.ramazanmamyrbek.categorymanagertgbot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageSender {
    private final TelegramBot telegramBot;

    public void sendMessage(String chatId, String text) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try{
            telegramBot.execute(sendMessage);
        }catch (TelegramApiException ex) {
            log.error(ex.getMessage());
        }
    }
}
