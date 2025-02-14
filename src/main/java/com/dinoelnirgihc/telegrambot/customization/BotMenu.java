package com.dinoelnirgihc.telegrambot.customization;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

public class BotMenu
{
    private static List<BotCommand> botMenu;

    public static List<BotCommand> createMenu()
    {
        botMenu = new ArrayList<BotCommand>();
        botMenu.add(new BotCommand("/start", "Hello my friend"));
        botMenu.add(new BotCommand("/register", "Register"));
        botMenu.add(new BotCommand("/mydata", "Get your data stored"));
        botMenu.add(new BotCommand("/deletedata", "Delete your data stored"));
        botMenu.add(new BotCommand("/help", "info how to use this bot"));

        return botMenu;
    }
}
