package com.github.loyaltycardwallet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.loyaltycardwallet.models.UserSpecifics;
import com.github.loyaltycardwallet.services.UserSpecificsService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ApplicationContext.class)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
class UserSpecificsControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserSpecificsService userSpecificsService;

    @InjectMocks
    private UserSpecificsController userSpecificsController;

    private JacksonTester<UserSpecifics> jsonUserSpecifics;

    private UserSpecifics manager1Specifics;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(userSpecificsController).build();

        manager1Specifics = UserSpecifics
                .builder()
                .firstName("Ronaldo")
                .lastName("FatOne")
                .email("manager1Specifics@email.com")
                .company(null)
                .build();

    }


    @Test()
    void testDoesMethodGetAllUsersSpecificsReturnsStatusCode200() throws Exception {
        List<UserSpecifics> userspecificsList = new ArrayList<>(Arrays.asList(manager1Specifics));

        BDDMockito.when(userSpecificsService.findAll())
                .thenReturn(userspecificsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users-specifics").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDoesMethodGetUserSpecificsByIdReturnStatusCode404() throws Exception {
        BDDMockito.when(userSpecificsService.findById(BDDMockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users-specifics/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void testDoesMethodAddUserSpecificsReturnsStatusCode202() throws Exception {
        BDDMockito.when(userSpecificsService.save(BDDMockito.any()))
                .thenReturn(manager1Specifics);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users-specifics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserSpecifics.write(manager1Specifics).getJson()))
                .andReturn().getResponse();

        log.warn(response.getContentAsString());


        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testDoesMethodUpdateUserSpecificsReturnsStatusCode404() throws Exception {

        BDDMockito.when(userSpecificsService.existById(BDDMockito.any()))
                .thenReturn(false);

        BDDMockito.when(userSpecificsService.save(BDDMockito.any()))
                .thenReturn(manager1Specifics);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/users-specifics/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserSpecifics.write(manager1Specifics).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDoesMethodUpdateUserSpecificsReturnsStatusCode204() throws Exception {
        BDDMockito.when(userSpecificsService.existById(BDDMockito.any()))
                .thenReturn(true);

        BDDMockito.when(userSpecificsService.save(BDDMockito.any()))
                .thenReturn(manager1Specifics);

        manager1Specifics.setFirstName("newTestName");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/users-specifics/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserSpecifics.write(manager1Specifics).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteUserSpecificsByIdReturnsStatusCode204() throws Exception {
        BDDMockito.when(userSpecificsService.existById(BDDMockito.any()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users-specifics/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteUserSpecificsByIdReturnsStatusCode404() throws Exception {
        BDDMockito.when(userSpecificsService.existById(BDDMockito.any()))
                .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users-specifics/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

}
