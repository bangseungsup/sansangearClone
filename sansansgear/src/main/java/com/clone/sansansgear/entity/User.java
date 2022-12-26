package com.clone.sansansgear.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;

    @Column
    private String password;

    @Column
    private String username;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bucket> bucketList = new ArrayList<>();

    public User(String userId, String password, String username, UserRoleEnum role){
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.role = role;
    }



    // 영문 대소문자, 숫자, 특수문자 다 포함해줄 것



//    @Column
//    private int phoneNumber;
//
//    @Column
//    private String email;


}
