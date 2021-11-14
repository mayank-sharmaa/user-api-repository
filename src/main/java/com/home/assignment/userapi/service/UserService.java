package com.home.assignment.userapi.service;

import com.home.assignment.userapi.entity.UserEntity;
import com.home.assignment.userapi.model.User;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;


public interface UserService {

    void createUser(UUID requestId, User createUser);

    List<UserEntity> getAllUsers();

    Long deleteUser(String username, Integer version);

    UserEntity updateUserRole(User user);

    List<User> getAllUserAndRoleInUnit(Long unitId);

    List<User> getAllUserWithAtleastOneValidRoleAtGivenTime(Long unitId, ZonedDateTime dateTime);
}
