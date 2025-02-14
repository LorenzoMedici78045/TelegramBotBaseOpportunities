package com.dinoelnirgihc.telegrambot.customization;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MarkupFactory
{
    public static InlineKeyboardMarkup getInlineMarkup(String nameEditManag)
    {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Да");
        inlineKeyboardButton1.setCallbackData("YES_BUTTON_" + nameEditManag);
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Нет");
        inlineKeyboardButton2.setCallbackData("NO_BUTTON_" + nameEditManag);
        rowInline1.add(inlineKeyboardButton1);
        rowInline1.add(inlineKeyboardButton2);
        rowsInline.add(rowInline1);
        keyboardMarkup.setKeyboard(rowsInline);
        return keyboardMarkup;
    }

}
