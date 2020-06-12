package com.github.loyaltycardwallet.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
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

    @NotBlank(message = "Email is mandatory")
    private String email;

    @OneToOne
    private Company company;

    @OneToMany
    private List<StampCardProgress> stampCardProgresses;
}
