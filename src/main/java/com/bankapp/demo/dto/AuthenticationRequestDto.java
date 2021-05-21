// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequestDto implements Serializable {

    private String username;
    private String password;

}
