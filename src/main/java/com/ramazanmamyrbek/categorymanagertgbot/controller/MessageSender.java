package com.ramazanmamyrbek.categorymanagertgbot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


/**
 * MessageSender is a component responsible for sending messages to Telegram users.
 *
 * This class utilizes the TelegramBot API to send messages to specific chat IDs.
 * It handles exceptions that may occur during the message sending process and logs errors.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MessageSender {
    private final TelegramBot telegramBot;

    /**
     * Sends a message to a specified chat ID.
     *
     * @param chatId The unique identifier for the target chat.
     * @param text   The text message to be sent.
     */
    public void sendMessage(String chatId, String text) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException ex) {
            log.error(ex.getMessage());
        }
    }
}
