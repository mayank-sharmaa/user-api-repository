package com.home.assignment.userapi.service.impl;

import com.home.assignment.userapi.entity.UserRoleEntity;
import com.home.assignment.userapi.error.ErrorCodes;
import com.home.assignment.userapi.exception.UserApiException;
import com.home.assignment.userapi.model.UserRole;
import com.home.assignment.userapi.repository.UserRoleEntityRepository;
import com.home.assignment.userapi.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleEntityRepository userRoleEntityRepository;

    public UserRoleServiceImpl(UserRoleEntityRepository userRoleEntityRepository) {
        this.userRoleEntityRepository = userRoleEntityRepository;
    }


    @Override
    @Transactional
    public void createUserRole(UserRole userRole) {
        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .version(1)
                .userId(userRole.getUserId())
                .unitId(userRole.getUnitId())
                .roleId(userRole.getRoleId())
                .validFrom(userRole.getValidFrom()!=null ? userRole.getValidFrom(): ZonedDateTime.now())
                .validTo(userRole.getValidTo()!=null ? userRole.getValidTo(): null)
                .build();
        userRoleEntityRepository.save(userRoleEntity);

    }

    @Override
    @Transactional
    public UserRoleEntity updateUserRole(UserRole userRole) {
        Optional<UserRoleEntity> userRoleEntity = userRoleEntityRepository.findByUserIdAndRoleIdAndUnitId(userRole.getUserId(),userRole.getRoleId(),userRole.getUnitId());
        if(userRoleEntity.isPresent()){
            userRoleEntity.map(x->{
                x.setValidTo(userRole.getValidTo());
                x.setValidFrom(userRole.getValidFrom());
                if(!(x.getVersion().equals(userRole.getVersion()))) throw new UserApiException(ErrorCodes.VERSION_MISMATCH_ERROR.getErrorCode(), ErrorCodes.VERSION_MISMATCH_ERROR.getErrorMessage());
                x.setVersion(x.getVersion()+1);
                return userRoleEntity;
            });
            UserRoleEntity updatedUserRoleEnity= userRoleEntity.get();
            userRoleEntityRepository.save(updatedUserRoleEnity);
            return updatedUserRoleEnity;
        }else {
            throw new UserApiException(ErrorCodes.INVALID_UPDATE.getErrorCode(), ErrorCodes.INVALID_UPDATE.getErrorMessage());
        }

    }

    @Override
    @Transactional
    public Long deleteUserRole(Long userId, Long unitId, Long roleId,Integer version) {
        Optional<UserRoleEntity> userRoleEntity=userRoleEntityRepository.findByUserIdAndRoleIdAndUnitId(userId,roleId,unitId);
        userRoleEntity.ifPresent(x -> {
            if(!(x.getVersion().equals(version))) throw new UserApiException(ErrorCodes.VERSION_MISMATCH_ERROR.getErrorCode(), ErrorCodes.VERSION_MISMATCH_ERROR.getErrorMessage());

        });
      return   userRoleEntityRepository.deleteAllByUserIdAndUnitIdAndRoleIdAndVersionIs(userId,unitId,roleId,version);
    }

    @Override
    public List<UserRoleEntity> getAllUsersByUserIdAndUnitId(Long userId, Long unitId) {
        return userRoleEntityRepository.findByUserIdAndUnitId(userId,unitId);
    }

    @Override
    public List<UserRoleEntity> getAllValidUsersByUserIdAndUnitIdAtGivenTime(Long userId, Long unitId, ZonedDateTime givenTimeStamp) {
        return userRoleEntityRepository.findByUserIdAndUnitIdAndValidFromAfter(userId,unitId,givenTimeStamp);
    }
}
