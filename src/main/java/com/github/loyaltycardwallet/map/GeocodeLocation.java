package com.github.loyaltycardwallet.map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeLocation {

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lng")
    private String longitude;

}
