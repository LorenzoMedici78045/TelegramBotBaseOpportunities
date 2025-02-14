package com.dinoelnirgihc.telegrambot.entity;

import com.dinoelnirgihc.telegrambot.embeddable.Username;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USERS", schema = "public")
public class User
{
    @Id
    @Column(name = "ID")
    private Long id;

    @Embedded
    private Username username;

    @Override
    public String toString()
    {
        return "Пользователь был создан: " + "\n" + username.getCreatedAt().toString() + "\n" + "Данные: " + username.getFirstname() + " " + username.getLastname();
    }
}
