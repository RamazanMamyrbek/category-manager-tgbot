package com.ramazanmamyrbek.categorymanagertgbot.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * TelegramBot is a custom implementation of the TelegramLongPollingBot class,
 * which handles incoming updates from Telegram and delegates them to the UpdateController.
 *
 * This class retrieves the bot's username and token from application properties
 * and registers itself with the UpdateController during initialization.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;

    private final UpdateController updateController;

    /**
     * Initializes the Telegram bot by registering it with the UpdateController.
     */
    @PostConstruct
    void init() {
        updateController.register(this);
    }

    /**
     * Returns the username of the bot.
     *
     * @return The bot's username.
     */
    @Override
    public String getBotUsername() {
        return name;
    }

    /**
     * Returns the token of the bot used for authentication.
     *
     * @return The bot's token.
     */
    @Override
    public String getBotToken() {
        return token;
    }

    /**
     * Processes incoming updates from Telegram.
     * This method is called whenever a new update is received.
     *
     * @param update The update received from Telegram.
     */
    @Override
    public void onUpdateReceived(Update update) {
        updateController.processUpdate(update);
    }
}