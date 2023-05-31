package com.west2_5.model.request.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddUserRequest {

    private String password;

    private String phone;

    private String code;

}
