package com.ramazanmamyrbek.categorymanagertgbot.config;

import com.ramazanmamyrbek.categorymanagertgbot.controller.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * TelegramBotConfig class is responsible for configuring the Telegram Bot API within the Spring application.
 *
 * This configuration class sets up the {@link TelegramBotsApi} bean and registers the
 * Telegram bot instance, allowing the bot to receive and respond to messages.
 */
@Configuration
public class TelegramBotConfig {

    /**
     * Creates a TelegramBotsApi bean and registers the specified Telegram bot.
     *
     * @param telegramBot The instance of the Telegram bot to be registered.
     * @return A configured instance of TelegramBotsApi that allows the bot to function.
     * @throws TelegramApiException If there is an issue while registering the bot.
     */
    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(telegramBot);
        return telegramBotsApi;
    }
}

