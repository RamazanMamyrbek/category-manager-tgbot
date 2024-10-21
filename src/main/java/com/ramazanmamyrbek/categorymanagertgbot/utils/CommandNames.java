package com.ramazanmamyrbek.categorymanagertgbot.utils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

/**
 * Utility class that holds the command names used in the Telegram bot.
 * This class provides a centralized location for defining command strings,
 * making it easier to manage and maintain them throughout the application.
 */
@UtilityClass
@FieldDefaults(level = AccessLevel.PUBLIC)
public class CommandNames {
    public final String START = "/start";                // Command to start the bot
    public final String VIEW_TREE = "/viewTree";          // Command to view the category tree
    public final String ADD_ELEMENT = "/addElement";      // Command to add a new category
    public final String REMOVE_ELEMENT = "/removeElement"; // Command to remove an existing category
    public final String HELP = "/help";                    // Command to display help information
    public final String EXIT = "/exit";                    // Command to exit the current operation
    public static final String CLEAR = "/clear";           // Command to clear the category tree
}
