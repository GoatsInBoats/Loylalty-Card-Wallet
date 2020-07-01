package com.github.loyaltycardwallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.UUID;

@AllArgsConstructor
@Data
public class ManagerEditDTO {

    private UUID id;

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

    @Size(min = 3, max = 100)
    private String companyName;

    @Size(min = 3, max = 100)
    private String city;

    @Size(min = 5, max = 100)
    private String zipCode;

    @Size(min = 3, max = 100)
    private String street;

    private long localNumber;

    public String getFormattedAddress() {
        return this.street + " " + this.localNumber + ", " + this.city;
    }

}
