package com.dinoelnirgihc.telegrambot.configuration;

import com.dinoelnirgihc.telegrambot.controller.TgBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Slf4j
public class TgBotIniz
{
    private TgBot tgBot;

    @Autowired
    public TgBotIniz(TgBot tgBot)
    {
        this.tgBot = tgBot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        try
        {
            api.registerBot(tgBot);
        }
        catch (TelegramApiException e)
        {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
