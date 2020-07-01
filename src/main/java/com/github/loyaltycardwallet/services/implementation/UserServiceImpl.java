package com.github.loyaltycardwallet.services.implementation;

import com.github.loyaltycardwallet.dto.ManagerRegisterAndEditDTO;
import com.github.loyaltycardwallet.dto.NormalUserRegisterAndEditDTO;
import com.github.loyaltycardwallet.models.User;
import com.github.loyaltycardwallet.repositories.UserRepository;
import com.github.loyaltycardwallet.services.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existById(UUID id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }

    @Override
    public void editNormalUserFields(@RequestBody @Valid NormalUserRegisterAndEditDTO normalUserEditDTO, User normalUser) {
        normalUser.setUsername(normalUserEditDTO.getUsername());
        normalUser.setPassword(new BCryptPasswordEncoder().encode(normalUserEditDTO.getPassword()));
        normalUser.getUserSpecifics().setFirstName(normalUserEditDTO.getFirstName());
        normalUser.getUserSpecifics().setLastName(normalUserEditDTO.getLastName());
        normalUser.getUserSpecifics().setEmail(normalUserEditDTO.getEmail());
    }

    @Override
    public void editManagerFields(@RequestBody @Valid ManagerRegisterAndEditDTO managerEditDTO, User manager) {
        String managerAddress = manager.getUserSpecifics().getCompany().getFormattedAddress();
        String managerEditDtoAddress = managerEditDTO.getFormattedAddress();

        manager.setUsername(managerEditDTO.getUsername());
        manager.setPassword(new BCryptPasswordEncoder().encode(managerEditDTO.getPassword()));
        manager.getUserSpecifics().setFirstName(managerEditDTO.getFirstName());
        manager.getUserSpecifics().setLastName(managerEditDTO.getLastName());
        manager.getUserSpecifics().setEmail(managerEditDTO.getEmail());
        manager.getUserSpecifics().getCompany().setCompanyName(managerEditDTO.getCompanyName());

        if (managerAddress.equals(managerEditDtoAddress)) {
            manager.getUserSpecifics().getCompany().setCity(managerEditDTO.getCity());
            manager.getUserSpecifics().getCompany().setZipCode(managerEditDTO.getZipCode());
            manager.getUserSpecifics().getCompany().setStreet(managerEditDTO.getStreet());
            manager.getUserSpecifics().getCompany().setLocalNumber(managerEditDTO.getLocalNumber());
        } else {
            manager.getUserSpecifics().getCompany().setCity(managerEditDTO.getCity());
            manager.getUserSpecifics().getCompany().setZipCode(managerEditDTO.getZipCode());
            manager.getUserSpecifics().getCompany().setStreet(managerEditDTO.getStreet());
            manager.getUserSpecifics().getCompany().setLocalNumber(managerEditDTO.getLocalNumber());

            String[] coordinates = null;
            try {
                coordinates = new RegisterServiceImpl().getLongitudeAndLatitude(managerEditDtoAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (coordinates != null) {
                manager.getUserSpecifics().getCompany().setLongitude(coordinates[0]);
                manager.getUserSpecifics().getCompany().setLatitude(coordinates[1]);
            }
        }
    }

}
