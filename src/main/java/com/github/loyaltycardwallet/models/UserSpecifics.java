package com.github.loyaltycardwallet.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class UserSpecifics {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(generator = "uuid")
    private UUID id;

    private String firstName;

    private String lastName;

    @NotBlank(message = "email is mandatory")
    @Size(min = 3, max = 200, message = "email length out of range")
    @Email(message = "email invalid")
    private String email;

    @OneToOne
    private Company company;
}
