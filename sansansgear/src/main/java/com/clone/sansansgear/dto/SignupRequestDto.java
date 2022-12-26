package com.clone.sansansgear.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    private String userId;

    private String password;

    private String userName;

    private boolean admin = false;
    private String adminToken = "";

}
