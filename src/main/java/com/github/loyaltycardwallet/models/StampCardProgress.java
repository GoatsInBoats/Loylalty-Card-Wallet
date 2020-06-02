package com.github.loyaltycardwallet.models;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
public class StampCardProgress {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @ManyToOne
    private UserSpecifics userSpecifics;

    @ManyToOne
    private StampCard stampCard;

    private int actualScore;

}
