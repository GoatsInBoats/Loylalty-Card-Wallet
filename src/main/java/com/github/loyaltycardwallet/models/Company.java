package com.github.loyaltycardwallet.models;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
public class Company {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(generator = "uuid")
    private UUID id;

    private String companyName;

    private String city;

    private String zipCode;

    private String street;

    private long localNumber;

    @OneToOne
    private StampCard stampCard;

    public Company(String companyName, String city, String zipCode, String street, long localNumber) {
        this.companyName = companyName;
        this.city = city;
        this.zipCode = zipCode;
        this.street = street;
        this.localNumber = localNumber;
    }

}
