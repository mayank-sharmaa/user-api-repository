package com.home.assignment.userapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version",nullable = false)
    private Integer version;

    @Column(name = "name",nullable = false)
    private String name;



    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<UserRoleEntity> userRoles;



    public UserEntity(Integer version, String name) {
        this.version = version;
        this.name = name;
    }
}
