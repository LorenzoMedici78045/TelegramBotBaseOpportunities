package com.dinoelnirgihc.telegrambot.configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@Slf4j
@PropertySource("application.properties")
public class TgBotConfig
{
    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;
}
