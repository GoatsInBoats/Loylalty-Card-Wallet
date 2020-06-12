package com.github.loyaltycardwallet.services.implementation;


import com.github.loyaltycardwallet.dto.ManagerRegisterDTO;
import com.github.loyaltycardwallet.dto.NormalUserRegisterDTO;
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
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Service
class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;
    private final UserSpecificsRepository userSpecificsRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User normalUserRegister(NormalUserRegisterDTO normalUserRegisterDTO) {
        UserSpecifics userSpecifics = UserSpecifics
                .builder()
                .firstName(normalUserRegisterDTO.getFirstName())
                .lastName(normalUserRegisterDTO.getLastName())
                .email(normalUserRegisterDTO.getEmail())
                .company(null)
                .stampCardProgresses(null)
                .build();

        userSpecificsRepository.save(userSpecifics);

        User user = User
                .builder()
                .username(normalUserRegisterDTO.getUsername())
                .password(passwordEncoder.encode(normalUserRegisterDTO.getPassword()))
                .active(1)
                .roles("ROLE_USER")
                .userSpecifics(userSpecifics)
                .build();

        userRepository.save(user);

        return user;
    }


    @SneakyThrows
    @Override
    public User managerRegister(ManagerRegisterDTO managerRegisterDTO) {

        String[] longitudeAndLatitude = getLongitudeAndLatitude(managerRegisterDTO);


        Company company = Company
                .builder()
                .companyName(managerRegisterDTO.getCompanyName())
                .city(managerRegisterDTO.getCity())
                .zipCode(managerRegisterDTO.getZipCode())
                .street(managerRegisterDTO.getStreet())
                .localNumber(managerRegisterDTO.getLocalNumber())
                .longitude(longitudeAndLatitude[0])
                .latitude(longitudeAndLatitude[1])
                .stampCard(null)
                .build();

        companyRepository.save(company);

        UserSpecifics userSpecifics = UserSpecifics
                .builder()
                .firstName(managerRegisterDTO.getFirstName())
                .lastName(managerRegisterDTO.getLastName())
                .email(managerRegisterDTO.getEmail())
                .company(company)
                .stampCardProgresses(null)
                .build();

        userSpecificsRepository.save(userSpecifics);

        User user = User
                .builder()
                .username(managerRegisterDTO.getUsername())
                .password(passwordEncoder.encode(managerRegisterDTO.getPassword()))
                .active(1)
                .roles("ROLE_MANAGER")
                .userSpecifics(userSpecifics)
                .build();

        userRepository.save(user);

        return user;
    }

    private String[] getLongitudeAndLatitude(ManagerRegisterDTO managerRegisterDTO) throws IOException {

        GeocodeController geocodeController = new GeocodeController();

        String encodedAddress = URLEncoder.encode(managerRegisterDTO.getFormattedAddress(), "UTF-8");
        GeocodeResult fullGeoJson = geocodeController.getGeocode(encodedAddress);
        List<GeocodeObject> geocodeObjectList = fullGeoJson.getResults();
        GeocodeObject geocodeObject = geocodeObjectList.get(0);
        GeocodeGeometry geocodeGeometry = geocodeObject.getGeometry();

        String longitude = geocodeGeometry.getGeocodeLocation().getLongitude();
        String latitude = geocodeGeometry.getGeocodeLocation().getLatitude();

        return new String[]{longitude, latitude};
    }

}
