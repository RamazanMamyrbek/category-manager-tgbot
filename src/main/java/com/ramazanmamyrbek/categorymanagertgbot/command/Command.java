package com.ramazanmamyrbek.categorymanagertgbot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Command interface for executing commands in a Telegram bot.
 *
 * This interface follows the "Command" design pattern and is intended
 * to execute commands received from the user through the Telegram API.
 */
public interface Command {
    /**
     * Executes the command based on the provided update.
     *
     * @param update An Update object representing data received from the user,
     *               including the message and chat information.
     */
    void execute(Update update);
}
