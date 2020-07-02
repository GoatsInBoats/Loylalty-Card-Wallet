package com.github.loyaltycardwallet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.loyaltycardwallet.models.StampCardProgress;
import com.github.loyaltycardwallet.services.StampCardProgressService;
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
class StampCardProgressControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private StampCardProgressService stampCardProgressService;

    @InjectMocks
    private StampCardProgressController stampCardProgressController;

    private JacksonTester<StampCardProgress> scpJson;

    private StampCardProgress stampCardProgress1;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(stampCardProgressController).build();

        stampCardProgress1 = StampCardProgress
                .builder()
                .actualScore(10)
                .stampCard(null)
                .build();
    }


    @Test
    void testDoesMethodGetAllStampCardProgressesReturnsStatusCode200() throws Exception {
        List<StampCardProgress> userList = new ArrayList<>(Arrays.asList(stampCardProgress1));

        BDDMockito.when(stampCardProgressService.findAll())
                .thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stampcards-progresses").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDoesMethodGetStampCardProgressByIdReturnStatusCode404() throws Exception {
        BDDMockito.when(stampCardProgressService.findById(BDDMockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stampcards-progresses/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void testDoesMethodAddStampCardProgressReturnsStatusCode202() throws Exception {
        BDDMockito.when(stampCardProgressService.save(BDDMockito.any()))
                .thenReturn(stampCardProgress1);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/stampcards-progresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scpJson.write(stampCardProgress1).getJson()))
                .andReturn().getResponse();

        log.warn(response.getContentAsString());


        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testDoesMethodUpdateStampCardProgressReturnsStatusCode404() throws Exception {

        BDDMockito.when(stampCardProgressService.existById(BDDMockito.any()))
                .thenReturn(false);

        BDDMockito.when(stampCardProgressService.save(BDDMockito.any()))
                .thenReturn(stampCardProgress1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/stampcards-progresses/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scpJson.write(stampCardProgress1).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDoesMethodUpdateStampCardProgressReturnsStatusCode204() throws Exception {
        BDDMockito.when(stampCardProgressService.existById(BDDMockito.any()))
                .thenReturn(true);

        BDDMockito.when(stampCardProgressService.save(BDDMockito.any()))
                .thenReturn(stampCardProgress1);

        stampCardProgress1.setActualScore(1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/stampcards-progresses/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scpJson.write(stampCardProgress1).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteStampCardProgressByIdReturnsStatusCode204() throws Exception {
        BDDMockito.when(stampCardProgressService.existById(BDDMockito.any()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/stampcards-progresses/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteStampCardProgressByIdReturnsStatusCode404() throws Exception {
        BDDMockito.when(stampCardProgressService.existById(BDDMockito.any()))
                .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/stampcards-progresses/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }


}
