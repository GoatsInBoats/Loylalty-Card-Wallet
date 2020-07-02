package com.github.loyaltycardwallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class NormalUserRegisterAndEditDTO {
    @Size(min = 5, max = 100)
    private String username;

    @Size(min = 5, max = 100)
    private String password;

    @Size(min = 2, max = 100)
    private String firstName;

    @Size(min = 3, max = 100)
    private String lastName;

    @Size(min = 9, max = 100)
    private String email;
}
