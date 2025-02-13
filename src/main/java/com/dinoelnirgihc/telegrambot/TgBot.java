package com.dinoelnirgihc.telegrambot;

import com.dinoelnirgihc.telegrambot.configuration.TgBotConfig;
import com.dinoelnirgihc.telegrambot.model.BotMenu;
import com.dinoelnirgihc.telegrambot.service.UserService;
import com.vdurmont.emoji.EmojiParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import static com.dinoelnirgihc.telegrambot.model.Text.HELP;

@Component
@Slf4j
public class TgBot extends TelegramLongPollingBot
{
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
        }
        catch (TelegramApiException e)
        {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        if(update.hasMessage() && update.getMessage().hasText())
        {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch(message)
            {
                case "/start":
                    startCommandReceived(chatId, update.getMessage());
                    break;
                case "/register":  registerCommandReceived(chatId, update.getMessage());
                    break;
                case "/mydata": dataCommandReceived(chatId, update.getMessage());
                    break;
                case "/deletedata": deleteCommandReceived(chatId, update.getMessage());
                    break;
                case "/help": helpCommandReceived(chatId);
                    break;
                    default: break;
            }
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

    private void registerCommandReceived(long chatId, Message message)
    {
        String data = userService.registerUserData(message);

        sendMessage(chatId, data);
    }

    private void deleteCommandReceived(long chatId, Message message)
    {
        String delete = userService.deleteUserData(message);
        sendMessage(chatId, delete);
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
}
