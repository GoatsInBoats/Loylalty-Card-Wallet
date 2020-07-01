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

    private String longitude;

    private String latitude;

    @OneToOne
    private StampCard stampCard;

    public String getFormattedAddress() {
        return this.street + " " + this.localNumber + ", " + this.city;
    }
}
