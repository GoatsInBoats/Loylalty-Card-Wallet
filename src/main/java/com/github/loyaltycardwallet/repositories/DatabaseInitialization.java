package com.github.loyaltycardwallet.repositories;

import com.github.loyaltycardwallet.models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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


        Reward reward1 = Reward
                .builder()
                .rewardDescription("SOMETHING COOL MAN")
                .build();
        Reward reward2 = Reward
                .builder()
                .rewardDescription("AWESOME THING DUDE")
                .build();
        Reward reward3 = Reward
                .builder()
                .rewardDescription("GREAT DING FELLA")
                .build();
        Reward reward4 = Reward
                .builder()
                .rewardDescription("ANYTHING U WANT GURL")
                .build();
        Reward reward5 = Reward
                .builder()
                .rewardDescription("SUPER DOUG")
                .build();
        Reward reward6 = Reward
                .builder()
                .rewardDescription("TINY WINY KITTY")
                .build();

        this.rewardRepository.saveAll(Arrays.asList(
                reward1,
                reward2,
                reward3,
                reward4,
                reward5,
                reward6
        ));


        StampCardReward stampCardReward1 = StampCardReward
                .builder()
                .position(5)
                .reward(reward1)
                .build();
        StampCardReward stampCardReward2 = StampCardReward
                .builder()
                .position(10)
                .reward(reward2)
                .build();
        StampCardReward stampCardReward3 = StampCardReward
                .builder()
                .position(8)
                .reward(reward3)
                .build();
        StampCardReward stampCardReward4 = StampCardReward
                .builder()
                .position(10)
                .reward(reward4)
                .build();
        StampCardReward stampCardReward5 = StampCardReward
                .builder()
                .position(5)
                .reward(reward5)
                .build();
        StampCardReward stampCardReward6 = StampCardReward
                .builder()
                .position(10)
                .reward(reward6)
                .build();

        this.stampCardRewardRepository.saveAll(Arrays.asList(
                stampCardReward1,
                stampCardReward2,
                stampCardReward3,
                stampCardReward4,
                stampCardReward5,
                stampCardReward6
        ));


        StampCard stampCard1 = StampCard
                .builder()
                .score(10)
                .stampCardReward(Arrays.asList(stampCardReward1, stampCardReward2))
                .build();

        StampCard stampCard2 = StampCard
                .builder()
                .score(10)
                .stampCardReward(Arrays.asList(stampCardReward3))
                .build();

        StampCard stampCard3 = StampCard
                .builder()
                .score(10)
                .stampCardReward(Arrays.asList(stampCardReward4))
                .build();

        StampCard stampCard4 = StampCard
                .builder()
                .score(10)
                .stampCardReward(Arrays.asList(stampCardReward5, stampCardReward6))
                .build();


        this.stampCardRepository.saveAll(Arrays.asList(
                stampCard1,
                stampCard2,
                stampCard3,
                stampCard4
        ));


        StampCardProgress stampCardProgress1 = StampCardProgress
                .builder()
                .actualScore(10)
                .stampCard(stampCard1)
                .build();
        StampCardProgress stampCardProgress2 = StampCardProgress
                .builder()
                .actualScore(4)
                .stampCard(stampCard2)
                .build();
        StampCardProgress stampCardProgress3 = StampCardProgress
                .builder()
                .actualScore(2)
                .stampCard(stampCard3)
                .build();
        StampCardProgress stampCardProgress4 = StampCardProgress
                .builder()
                .actualScore(8)
                .stampCard(stampCard4)
                .build();

        this.stampCardProgressRepository.saveAll(Arrays.asList(
                stampCardProgress1,
                stampCardProgress2,
                stampCardProgress3,
                stampCardProgress4
        ));


        Company commpany1 = Company
                .builder()
                .companyName("Great company")
                .city("Krakow")
                .zipCode("12-345")
                .street("Florianska")
                .localNumber(17)
                .latitude("50.062938")
                .longitude("19.939988")
                .stampCard(stampCard1)
                .build();

        Company company2 = Company
                .builder()
                .companyName("Even better than great company")
                .city("Krakow")
                .zipCode("31-877")
                .street("Os Avia")
                .localNumber(3)
                .latitude("50.079930")
                .longitude("20.004029")
                .stampCard(stampCard2)
                .build();

        Company company3 = Company
                .builder()
                .companyName("Turbo awesome company")
                .city("Krakow")
                .zipCode("30-364")
                .street("Pychowicka")
                .localNumber(18)
                .latitude("50.031895")
                .longitude("19.914740")
                .stampCard(stampCard3)
                .build();


        Company company4 = Company
                .builder()
                .companyName("Fridge stolen food")
                .city("Krakow")
                .zipCode("31-043")
                .street("Stolarska")
                .localNumber(9)
                .latitude("50.048428")
                .longitude("19.961411")
                .stampCard(stampCard4)
                .build();


        this.companyRepository.saveAll(Arrays.asList(
                commpany1,
                company2,
                company3,
                company4
        ));


        UserSpecifics adamSpecifics = UserSpecifics
                .builder()
                .firstName("Adam")
                .lastName("Pietras")
                .email("testadamp@gmail.com")
                .stampCardProgresses(Arrays.asList(stampCardProgress1))
                .build();

        UserSpecifics bartekSpecifics = UserSpecifics
                .builder()
                .firstName("Bartek")
                .lastName("Slodyczka")
                .email("bartekxd@gmail.com")
                .stampCardProgresses(Arrays.asList(stampCardProgress2))
                .build();

        UserSpecifics martaSpecifics = UserSpecifics
                .builder()
                .firstName("Marta")
                .lastName("Sibielak")
                .email("martaxdxd@gmail.com")
                .stampCardProgresses(Arrays.asList(stampCardProgress3))
                .build();

        UserSpecifics mociekSpecifics = UserSpecifics
                .builder()
                .firstName("Mociek")
                .lastName("CoUciek")
                .email("traitor@gmail.com")
                .stampCardProgresses(Arrays.asList(stampCardProgress4))
                .build();


        UserSpecifics manager1Specifics = UserSpecifics
                .builder()
                .firstName("Ronaldo")
                .lastName("FatOne")
                .email("ronaldo@email.com")
                .company(commpany1)
                .build();

        UserSpecifics manager2Specifics = UserSpecifics
                .builder()
                .firstName("Adam")
                .lastName("Malysz")
                .email("lecadamlec@email.com")
                .company(company2)
                .build();

        UserSpecifics manager3Specifics = UserSpecifics
                .builder()
                .firstName("Adamo")
                .lastName("Romano")
                .email("angular@email.com")
                .company(company3)
                .build();

        UserSpecifics manager4Specifics = UserSpecifics
                .builder()
                .firstName("Adi")
                .lastName("Wii")
                .email("adiiwii@email.com")
                .company(company4)
                .build();


        UserSpecifics adminSpecifics = UserSpecifics
                .builder()
                .firstName("AdminName")
                .lastName("AdminLastName")
                .email("TestAdmin@email.com")
                .build();


        this.userSpecificsRepository.saveAll(Arrays.asList(
                adamSpecifics,
                adminSpecifics,
                martaSpecifics,
                bartekSpecifics,
                mociekSpecifics,
                manager1Specifics,
                manager2Specifics,
                manager3Specifics,
                manager4Specifics
        ));


        User admin = User
                .builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .permissions("ACCESS_TEST1,ACCESS_TEST2")
                .userSpecifics(adminSpecifics)
                .active(1)
                .build();

        User adam = User
                .builder()
                .username("adam")
                .password(passwordEncoder.encode("adam123"))
                .roles("USER")
                .permissions("ACCESS_TEST1")
                .userSpecifics(adamSpecifics)
                .active(1)
                .build();

        User bartek = User
                .builder()
                .username("bartek")
                .password(passwordEncoder.encode("bartek123"))
                .roles("USER")
                .permissions("ACCESS_TEST1")
                .userSpecifics(bartekSpecifics)
                .active(1)
                .build();

        User marta = User
                .builder()
                .username("marta")
                .password(passwordEncoder.encode("marta123"))
                .roles("USER")
                .permissions("ACCESS_TEST1")
                .userSpecifics(martaSpecifics)
                .active(1)
                .build();

        User mociek = User
                .builder()
                .username("mociek")
                .password(passwordEncoder.encode("mociek123"))
                .roles("USER")
                .permissions("ACCESS_TEST1")
                .userSpecifics(mociekSpecifics)
                .active(1)
                .build();

        User malysz = User
                .builder()
                .username("malysz")
                .password(passwordEncoder.encode("malysz123"))
                .roles("MANAGER")
                .permissions("ACCESS_TEST2")
                .userSpecifics(manager2Specifics)
                .active(1)
                .build();
        User ronaldo = User
                .builder()
                .username("ronaldo")
                .password(passwordEncoder.encode("ronaldo123"))
                .roles("MANAGER")
                .permissions("ACCESS_TEST2")
                .userSpecifics(manager1Specifics)
                .active(1)
                .build();


        User roman = User
                .builder()
                .username("roman")
                .password(passwordEncoder.encode("roman123"))
                .roles("MANAGER")
                .permissions("ACCESS_TEST2")
                .userSpecifics(manager3Specifics)
                .active(1)
                .build();
        User adi = User
                .builder()
                .username("adi")
                .password(passwordEncoder.encode("adi123"))
                .roles("MANAGER")
                .permissions("ACCESS_TEST2")
                .userSpecifics(manager4Specifics)
                .active(1)
                .build();

        this.userRepository.saveAll(Arrays.asList(
                admin,
                adam,
                bartek,
                marta,
                mociek,
                ronaldo,
                malysz,
                roman,
                adi
        ));

    }
}

