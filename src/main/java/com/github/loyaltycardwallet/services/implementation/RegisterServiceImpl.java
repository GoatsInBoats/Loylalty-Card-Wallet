package com.github.loyaltycardwallet.services.implementation;


import com.github.loyaltycardwallet.dto.ManagerRegisterAndEditDTO;
import com.github.loyaltycardwallet.dto.NormalUserRegisterAndEditDTO;
import com.github.loyaltycardwallet.map.GeocodeController;
import com.github.loyaltycardwallet.map.GeocodeGeometry;
import com.github.loyaltycardwallet.map.GeocodeObject;
import com.github.loyaltycardwallet.map.GeocodeResult;
import com.github.loyaltycardwallet.models.Company;
import com.github.loyaltycardwallet.models.User;
import com.github.loyaltycardwallet.models.UserSpecifics;
import com.github.loyaltycardwallet.repositories.CompanyRepository;
import com.github.loyaltycardwallet.repositories.UserRepository;
import com.github.loyaltycardwallet.repositories.UserSpecificsRepository;
import com.github.loyaltycardwallet.services.RegisterService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Service
class RegisterServiceImpl implements RegisterService {
    private UserRepository userRepository;
    private UserSpecificsRepository userSpecificsRepository;
    private CompanyRepository companyRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public User normalUserRegister(NormalUserRegisterAndEditDTO normalUserRegisterAndEditDTO) {
        UserSpecifics userSpecifics = UserSpecifics
                .builder()
                .firstName(normalUserRegisterAndEditDTO.getFirstName())
                .lastName(normalUserRegisterAndEditDTO.getLastName())
                .email(normalUserRegisterAndEditDTO.getEmail())
                .company(null)
                .stampCardProgresses(null)
                .build();

        userSpecificsRepository.save(userSpecifics);

        User user = User
                .builder()
                .username(normalUserRegisterAndEditDTO.getUsername())
                .password(passwordEncoder.encode(normalUserRegisterAndEditDTO.getPassword()))
                .active(1)
                .roles("ROLE_USER")
                .userSpecifics(userSpecifics)
                .build();

        userRepository.save(user);

        return user;
    }


    @SneakyThrows
    @Override
    public User managerRegister(ManagerRegisterAndEditDTO managerRegisterAndEditDTO) {

        String[] longitudeAndLatitude = getLongitudeAndLatitude(managerRegisterAndEditDTO.getFormattedAddress());


        Company company = Company
                .builder()
                .companyName(managerRegisterAndEditDTO.getCompanyName())
                .city(managerRegisterAndEditDTO.getCity())
                .zipCode(managerRegisterAndEditDTO.getZipCode())
                .street(managerRegisterAndEditDTO.getStreet())
                .localNumber(managerRegisterAndEditDTO.getLocalNumber())
                .longitude(longitudeAndLatitude[0])
                .latitude(longitudeAndLatitude[1])
                .stampCard(null)
                .build();

        companyRepository.save(company);

        UserSpecifics userSpecifics = UserSpecifics
                .builder()
                .firstName(managerRegisterAndEditDTO.getFirstName())
                .lastName(managerRegisterAndEditDTO.getLastName())
                .email(managerRegisterAndEditDTO.getEmail())
                .company(company)
                .stampCardProgresses(null)
                .build();

        userSpecificsRepository.save(userSpecifics);

        User user = User
                .builder()
                .username(managerRegisterAndEditDTO.getUsername())
                .password(passwordEncoder.encode(managerRegisterAndEditDTO.getPassword()))
                .active(1)
                .roles("ROLE_MANAGER")
                .userSpecifics(userSpecifics)
                .build();

        userRepository.save(user);

        return user;
    }

    String[] getLongitudeAndLatitude(String formattedAddress) throws IOException {

        GeocodeController geocodeController = new GeocodeController();

        String encodedAddress = URLEncoder.encode(formattedAddress, "UTF-8");
        GeocodeResult fullGeoJson = geocodeController.getGeocode(encodedAddress);
        List<GeocodeObject> geocodeObjectList = fullGeoJson.getResults();
        GeocodeObject geocodeObject = geocodeObjectList.get(0);
        GeocodeGeometry geocodeGeometry = geocodeObject.getGeometry();

        String longitude = geocodeGeometry.getGeocodeLocation().getLongitude();
        String latitude = geocodeGeometry.getGeocodeLocation().getLatitude();

        return new String[]{longitude, latitude};
    }

}
