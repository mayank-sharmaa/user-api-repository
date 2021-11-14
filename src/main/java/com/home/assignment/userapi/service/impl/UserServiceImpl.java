package com.home.assignment.userapi.service.impl;

import com.home.assignment.userapi.dto.UserDTO;
import com.home.assignment.userapi.entity.UserEntity;
import com.home.assignment.userapi.error.ErrorCodes;
import com.home.assignment.userapi.exception.UserApiException;
import com.home.assignment.userapi.mapper.ObjectMapper;
import com.home.assignment.userapi.model.User;
import com.home.assignment.userapi.repository.UserEntityRepository;
import com.home.assignment.userapi.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserEntityRepository userEntityRepository;
    private final ObjectMapper objectMapper;

    public UserServiceImpl(UserEntityRepository userEntityRepository, ObjectMapper objectMapper) {
        this.userEntityRepository = userEntityRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void createUser(UUID requestId, User user) {
        Optional<UserEntity> existingUser=userEntityRepository.findByNameIgnoreCase(user.getUsername());
        existingUser.ifPresent(x -> {
            x.setVersion(x.getVersion()+1);
            x.setName(user.getUsername());
            userEntityRepository.save(existingUser.get());

        });
        if(existingUser.isEmpty()) {
            UserEntity userEntity = UserEntity.builder().version(1).name(user.getUsername()).build();
            userEntityRepository.save(userEntity);
        }
    }

    @Override
    @Transactional
    public List<UserEntity> getAllUsers() {
        return userEntityRepository.findAll();
    }

    @Override
    @Transactional
    public Long deleteUser(String username, Integer version) {
        Optional<UserEntity> userEntity=userEntityRepository.findByNameIgnoreCase(username);
        userEntity.ifPresent(x -> {
            if(!(x.getVersion().equals(version))) throw new UserApiException(ErrorCodes.VERSION_MISMATCH_ERROR.getErrorCode(), ErrorCodes.VERSION_MISMATCH_ERROR.getErrorMessage());

        });
        return   userEntityRepository.deleteAllByNameIgnoreCase(username);
    }

    @Override
    public UserEntity updateUserRole(User user) {
        Optional<UserEntity> userEntity = userEntityRepository.findByNameIgnoreCase(user.getUsername());
        if(userEntity.isEmpty() )  throw new UserApiException(ErrorCodes.INVALID_UPDATE.getErrorCode(), ErrorCodes.INVALID_UPDATE.getErrorMessage());
        userEntity.ifPresent(x -> {
            if(!(x.getVersion().equals(user.getVersion())))
                throw new UserApiException(ErrorCodes.VERSION_MISMATCH_ERROR.getErrorCode(), ErrorCodes.VERSION_MISMATCH_ERROR.getErrorMessage());
            x.setVersion(x.getVersion()+1);
            x.setName(user.getUsername());
            userEntityRepository.save(userEntity.get());

        });

      return userEntity.get();

    }

    @Override
    @Transactional
    public List<User> getAllUserAndRoleInUnit(Long unitId) {
        List<UserDTO> queryResult = userEntityRepository.findUserAndRoleByUnitId(unitId);
        return queryResult.stream()
                .map(objectMapper::customMappingToUser).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<User> getAllUserWithAtleastOneValidRoleAtGivenTime(Long unitId, ZonedDateTime dateTime) {
        List<UserDTO> queryResult = userEntityRepository.findUserEntitiesByUserRolesAfter(unitId,dateTime);
        return queryResult.stream()
                .map(objectMapper::customMappingToUser).collect(Collectors.toList());
    }
}
