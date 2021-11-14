package com.home.assignment.userapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@RequiredArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version",nullable = false)
    private Integer version;

    @Column(name = "name",nullable = false)
    private String name;

    public RoleEntity(Integer version, String name) {
        this.version = version;
        this.name = name;
    }

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<UserRoleEntity> userRoles;

}
