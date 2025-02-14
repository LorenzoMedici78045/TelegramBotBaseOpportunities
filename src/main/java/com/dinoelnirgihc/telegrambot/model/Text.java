package com.dinoelnirgihc.telegrambot.model;

import com.vdurmont.emoji.EmojiParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Text
{
    public final static String HELP = "Этот бот был создан, чтобы доказать вам," +
            " что приседания-пистолетиком благоприятно скажутся на развитии ваших коленей! Также:\n" +
            "       /start - выводит приветсвенное сообщение\n" +
            "       /register - регистрирует вас в системе\n" +
            "       /mydata - выводит информацию о вас\n" +
            "       /deletedata - позволит удалить ваши данные\n" +
            "       /help - вы уже нажали!";
    public final static String NOTDELETE = EmojiParser.parseToUnicode("Вы сделали правильный выбор," + " :cat:");
    public final static String DELETE = EmojiParser.parseToUnicode("Пользователь удален, " + ":crying_cat_face:");
    public final static String DELETEMES = "Вы правда хотите удалить данные?";
    public final static String REGISTERMES = "Хотите зарегистрироваться?";
    public final static List<String> registoryUser = new ArrayList<String>(Arrays.asList("Привет, ", " :wave:"));
    public final static List<String> registoryData = new ArrayList<String>(Arrays.asList("Вы зарегистрированы, ", " :cat:"));

}
