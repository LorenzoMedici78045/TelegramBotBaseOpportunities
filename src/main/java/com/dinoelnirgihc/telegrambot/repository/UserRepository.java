package com.dinoelnirgihc.telegrambot.repository;

import com.dinoelnirgihc.telegrambot.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
