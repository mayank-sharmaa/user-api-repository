package com.home.assignment.userapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "user_role")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@IdClass(UserRoleCompositeKey.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "version",nullable = false)
    private Integer version;

    @Id
    @Column(name = "userId",nullable = false)
    private Long userId;

    @Id
    @Column(name = "unitId",nullable = false)
    private Long unitId;

    @Id
    @Column(name = "roleId",nullable = false)
    private Long roleId;

    @Column(name = "validFrom")
    private ZonedDateTime validFrom;



    @Column(name = "validTo",columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime validTo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false,insertable = false, updatable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roleId", nullable = false,insertable = false, updatable = false)
    private RoleEntity roleEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unitId", nullable = false,insertable = false, updatable = false)
    private UnitEntity unitEntity;



    public UserRoleEntity(Integer version, Long userId, Long unitId, Long roleId, ZonedDateTime validFrom, ZonedDateTime validTo) {
        this.version = version;
        this.userId = userId;
        this.unitId = unitId;
        this.roleId = roleId;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }


}
