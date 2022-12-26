package com.clone.sansansgear.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    private String userId;
    private String password;

    private boolean admin = false;
    private String adminToken = "";

}
