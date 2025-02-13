package com.dinoelnirgihc.telegrambot.service;

import com.dinoelnirgihc.telegrambot.embeddable.Username;
import com.dinoelnirgihc.telegrambot.entity.User;
import com.dinoelnirgihc.telegrambot.repository.UserRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Slf4j
public class UserService
{
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public String registerUser(Message message)
    {
        if(userRepository.findById(message.getChatId()).isEmpty()) {

            Long chatId = message.getChatId();
            Chat chat = message.getChat();

            User user = User
                    .builder()
                    .id(chatId)
                    .username(Username.builder()
                            .firstname(chat.getFirstName())
                            .lastname(chat.getLastName())
                            .createdAt(new Timestamp(System.currentTimeMillis()))
                            .build())
                    .build();

            userRepository.save(user);
            log.info("user saved: " + user);

            return EmojiParser.parseToUnicode("Привет, " + user.getUsername().getFirstname() + " " + user.getUsername().getLastname() + ", " + ":wave:");
        }
        else
        {
            User user = userRepository.findById(message.getChatId()).orElseThrow();
            return EmojiParser.parseToUnicode("Привет, " + user.getUsername().getFirstname() + " " + user.getUsername().getLastname() + ", " + ":wave:");
        }

    }

    public String getUserData(Message message)
    {
        if(!userRepository.findById(message.getChatId()).isEmpty()) {
            return userRepository.findById(message.getChatId()).orElseThrow().toString();
        }
        else
        {
            return "Пользователь не найден";
        }
    }

    public String deleteUserData(Message message)
    {
         User user = userRepository.findById(message.getChatId()).orElseThrow();
         userRepository.delete(user);
         return EmojiParser.parseToUnicode("Пользователь удален, " + ":crying_cat_face:");
    }

    public String registerUserData(Message message)
    {
        if(userRepository.findById(message.getChatId()).isEmpty())
        {
            Long chatId = message.getChatId();
            Chat chat = message.getChat();

            User user = User
                    .builder()
                    .id(chatId)
                    .username(Username.builder()
                            .firstname(chat.getFirstName())
                            .lastname(chat.getLastName())
                            .createdAt(new Timestamp(System.currentTimeMillis()))
                            .build())
                    .build();

            userRepository.save(user);
            return EmojiParser.parseToUnicode("Вы зарегистрированы, " + user.getUsername().getFirstname() + " " + user.getUsername().getLastname() + "!" + " :cat:");
        }
        else
        {
            User user = userRepository.findById(message.getChatId()).orElseThrow();
            return EmojiParser.parseToUnicode("Вы зарегистрированы, " + user.getUsername().getFirstname() + " " + user.getUsername().getLastname() + "!" + " :cat:");
        }
    }
}
