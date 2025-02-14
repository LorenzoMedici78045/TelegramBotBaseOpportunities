package com.dinoelnirgihc.telegrambot.controller;

import com.dinoelnirgihc.telegrambot.configuration.TgBotConfig;
import com.dinoelnirgihc.telegrambot.customization.BotMenu;
import com.dinoelnirgihc.telegrambot.customization.MarkupFactory;
import com.dinoelnirgihc.telegrambot.service.UserService;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import static com.dinoelnirgihc.telegrambot.model.Text.*;

@Component
@Slf4j
public class TgBot extends TelegramLongPollingBot {
    private TgBotConfig botConf;
    private UserService userService;

    public TgBot(TgBotConfig botConf, UserService userService)
    {
        super(botConf.getToken());
        this.botConf = botConf;
        this.userService = userService;

        try
        {
            this.execute(new SetMyCommands(BotMenu.createMenu(), new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        if (update.hasMessage() && update.getMessage().hasText())
        {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (message) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage());
                    break;
                case "/register":
                    registerCommandReceived(chatId);
                    break;
                case "/mydata":
                    dataCommandReceived(chatId, update.getMessage());
                    break;
                case "/deletedata":
                    deleteCommandReceived(chatId);
                    break;
                case "/help":
                    helpCommandReceived(chatId);
                    break;
                default:
                    break;
            }
        } else if (update.hasCallbackQuery())
        {
            deleteCommandKey(update);
            registerCommandKey(update);
        }

    }
    @Override
    public String getBotUsername() {
        return botConf.getName();
    }

    private void startCommandReceived(long chatId, Message message)
    {
        String hello = userService.registerUser(message);
        sendMessage(chatId, hello);
    }

    private void dataCommandReceived(long chatId, Message message)
    {
        String data = userService.getUserData(message);

        sendMessage(chatId, data);
    }

    private void registerCommandReceived(long chatId)
    {
        sendMessage(chatId, REGISTERMES, MarkupFactory.getInlineMarkup("REGISTER"));
    }

    private void deleteCommandReceived(long chatId)
    {
        sendMessage(chatId, DELETEMES, MarkupFactory.getInlineMarkup("DELETE"));
    }

    private void deleteCommandKey(Update update)
    {
        String callback = update.getCallbackQuery().getData();
        if (callback.equals("YES_BUTTON_DELETE"))
        {
            userService.deleteUserData(update.getCallbackQuery().getMessage());
            sendEditMessage(update, DELETE);
        }
        else if(callback.equals("NO_BUTTON_DELETE"))
        {
            sendEditMessage(update, NOTDELETE);
        }
    }

    private void registerCommandKey(Update update)
    {
        String callback = update.getCallbackQuery().getData();
        if (callback.equals("YES_BUTTON_REGISTER"))
        {
            userService.registerUserData(update.getCallbackQuery().getMessage());
            sendEditMessage(update, EmojiParser.parseToUnicode(registoryData.get(0) + registoryData.get(1)));
        }
        else if(callback.equals("NO_BUTTON_REGISTER"))
        {
            sendEditMessage(update, EmojiParser.parseToUnicode("Ну и ладно, " + registoryData.get(1)));
        }
    }


    private void helpCommandReceived(long chatId)
    {
        sendMessage(chatId,  HELP);
    }

    private void sendMessage(Long chatId, String message)
    {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(message)
                .build();
        try
        {
            this.execute(sendMessage);
        }
        catch (TelegramApiException e)
        {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    private void sendMessage(Long chatId, String message, InlineKeyboardMarkup markup)
    {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(message)
                .build();
        sendMessage.setReplyMarkup(markup);
        try
        {
            this.execute(sendMessage);
        }
        catch (TelegramApiException e)
        {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    private void sendEditMessage(Update update, String message)
    {
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        EditMessageText messageText = new EditMessageText();
        messageText.setChatId(chatId);
        messageText.setText(message);
        messageText.setMessageId(messageId);
        try {
            execute(messageText);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
