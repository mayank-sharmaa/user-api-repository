package com.home.assignment.userapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleCompositeKey implements Serializable {

    @Column(name = "id")
    private Long id;
    @Column(name = "userId",nullable = false)
    private Long userId;
    @Column(name = "roleId",nullable = false)
    private Long roleId;
    @Column(name = "unitId",nullable = false)
    private Long unitId;
}
