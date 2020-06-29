package com.github.loyaltycardwallet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.loyaltycardwallet.models.User;
import com.github.loyaltycardwallet.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ApplicationContext.class)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private JacksonTester<User> jsonUser;

    private User testUser;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        testUser = User
                .builder()
                .id(UUID.fromString("ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .username("testUser")
                .password(new BCryptPasswordEncoder().encode("testUser123"))
                .roles("MANAGER")
                .permissions("ACCESS_TEST2")
                .userSpecifics(null)
                .active(1)
                .build();
    }


    @Test
    void testDoesMethodGetAllUsersReturnsStatusCode200() throws Exception {
        List<User> userList = new ArrayList<>(Arrays.asList(testUser));

        BDDMockito.when(userService.findAll())
                .thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDoesMethodGetUserByIdReturnStatusCode404() throws Exception {
        BDDMockito.when(userService.findById(BDDMockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void testDoesMethodAddUserReturnsStatusCode202() throws Exception {
        BDDMockito.when(userService.save(BDDMockito.any()))
                .thenReturn(testUser);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser.write(testUser).getJson()))
                .andReturn().getResponse();

        log.warn(response.getContentAsString());


        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testDoesMethodUpdateUserReturnsStatusCode404() throws Exception {

        BDDMockito.when(userService.existById(BDDMockito.any()))
                .thenReturn(false);

        BDDMockito.when(userService.save(BDDMockito.any()))
                .thenReturn(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/users/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser.write(testUser).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDoesMethodUpdateUserReturnsStatusCode204() throws Exception {
        BDDMockito.when(userService.existById(BDDMockito.any()))
                .thenReturn(true);

        BDDMockito.when(userService.save(BDDMockito.any()))
                .thenReturn(testUser);

        testUser.setUsername("KtestUser");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/users/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser.write(testUser).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteUserByIdReturnsStatusCode204() throws Exception {
        BDDMockito.when(userService.existById(BDDMockito.any()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteUserByIdReturnsStatusCode404() throws Exception {
        BDDMockito.when(userService.existById(BDDMockito.any()))
                .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

}
