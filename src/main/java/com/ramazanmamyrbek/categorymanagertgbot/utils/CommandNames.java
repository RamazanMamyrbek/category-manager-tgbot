package com.ramazanmamyrbek.categorymanagertgbot.utils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

@UtilityClass
@FieldDefaults(level = AccessLevel.PUBLIC)
public class CommandNames {
    public final String START = "/start";
    public final String VIEW_TREE = "/viewTree";
    public final String ADD_ELEMENT = "/addElement";
    public final String REMOVE_ELEMENT = "/removeElement";
    public final String HELP = "/help";
    public final String EXIT = "/exit";
    public static final String CLEAR = "/clear";
}
