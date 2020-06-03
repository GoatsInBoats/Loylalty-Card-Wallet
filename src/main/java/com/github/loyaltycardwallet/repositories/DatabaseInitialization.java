package com.github.loyaltycardwallet.repositories;

import com.github.loyaltycardwallet.models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DatabaseInitialization implements CommandLineRunner {

    private UserRepository userRepository;
    private UserSpecificsRepository userSpecificsRepository;
    private CompanyRepository companyRepository;
    private StampCardRepository stampCardRepository;
    private StampCardRewardRepository stampCardRewardRepository;
    private StampCardProgressRepository stampCardProgressRepository;
    private RewardRepository rewardRepository;
    private PasswordEncoder passwordEncoder;

    public DatabaseInitialization(UserRepository userRepository, UserSpecificsRepository userSpecificsRepository,
                                  CompanyRepository companyRepository, StampCardRepository stampCardRepository,
                                  StampCardRewardRepository stampCardRewardRepository,
                                  StampCardProgressRepository stampCardProgressRepository,
                                  RewardRepository rewardRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userSpecificsRepository = userSpecificsRepository;
        this.companyRepository = companyRepository;
        this.stampCardRepository = stampCardRepository;
        this.stampCardRewardRepository = stampCardRewardRepository;
        this.stampCardProgressRepository = stampCardProgressRepository;
        this.rewardRepository = rewardRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {


        StampCard stampCard1 = StampCard
                .builder()
                .score(10)
                .build();

        this.stampCardRepository.save(stampCard1);
        Company managerCompanySpecifics = Company
                .builder()
                .companyName("Testcompanyname")
                .city("Krakow")
                .zipCode("12-345")
                .street("Florianska")
                .localNumber(17)
                .stampCard(stampCard1)
                .build();


        List<Company> companyList = Arrays.asList(managerCompanySpecifics);
        this.companyRepository.saveAll(companyList);


        UserSpecifics adamSpecifics = UserSpecifics
                .builder()
                .firstName("Adam")
                .lastName("Pietras")
                .email("testadamp@gmail.com")
                .build();

        UserSpecifics adminSpecifics = UserSpecifics
                .builder()
                .firstName("AdminName")
                .lastName("AdminLastName")
                .email("TestAdmin@email.com")
                .build();

        UserSpecifics managerSpecifics = UserSpecifics
                .builder()
                .firstName("ManagerName")
                .lastName("ManagerLastName")
                .email("TestManager@email.com")
                .company(managerCompanySpecifics)
                .build();

        List<UserSpecifics> userSpecificsList = Arrays.asList(adamSpecifics, adminSpecifics, managerSpecifics);
        this.userSpecificsRepository.saveAll(userSpecificsList);

        User adam = User
                .builder()
                .username("adam")
                .password(passwordEncoder.encode("adam123"))
                .roles("USER")
                .permissions("ACCESS_TEST1")
                .userSpecifics(adamSpecifics)
                .active(1)
                .build();

        User admin = User
                .builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .permissions("ACCESS_TEST1,ACCESS_TEST2")
                .userSpecifics(adminSpecifics)
                .active(1)
                .build();

        User manager = User
                .builder()
                .username("manager")
                .password(passwordEncoder.encode("manager123"))
                .roles("MANAGER")
                .permissions("ACCESS_TEST1")
                .userSpecifics(managerSpecifics)
                .active(1)
                .build();

        List<User> users = Arrays.asList(adam, admin, manager);
        this.userRepository.saveAll(users);

        Reward reward1 = Reward
                .builder()
                .rewardDescription("SOMETHING COOL MAN")
                .build();

        this.rewardRepository.save(reward1);

        StampCardReward stampCardReward1 = StampCardReward
                .builder()
                .position(10)
                .reward(reward1)
                .build();

        this.stampCardRewardRepository.save(stampCardReward1);

        StampCardProgress stampCardProgress1 = StampCardProgress
                .builder()
                .actualScore(7)
                .stampCard(stampCard1)
                .userSpecifics(adamSpecifics)
                .build();

        this.stampCardProgressRepository.save(stampCardProgress1);


    }


}

