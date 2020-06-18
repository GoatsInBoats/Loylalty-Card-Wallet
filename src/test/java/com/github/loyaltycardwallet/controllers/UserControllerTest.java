package com.github.loyaltycardwallet.controllers;

import com.github.loyaltycardwallet.models.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(classes = ApplicationContext.class)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserController userController;

    @Test
    void testDoesMethodGetAllUsersReturnsStatusCode200() throws Exception {

        User marta = User
                .builder()
                .username("marta")
                .build();

        User mociek = User
                .builder()
                .username("mociek")
                .build();

        User malysz = User
                .builder()
                .username("malysz")
                .build();

        List<User> userList = new ArrayList<>(Arrays.asList(marta, malysz, mociek));

        BDDMockito.when(userController.getAllUsers())
                .thenReturn(ResponseEntity.ok(userList));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDoesMethodGetUserByIdReturnStatusCode404() throws Exception {

        BDDMockito.when(userController.getUserById(UUID.randomUUID()))
                .thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void addUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUserById() {
    }
}
