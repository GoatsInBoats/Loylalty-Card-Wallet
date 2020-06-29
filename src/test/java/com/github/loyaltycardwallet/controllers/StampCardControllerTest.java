package com.github.loyaltycardwallet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.loyaltycardwallet.models.StampCard;
import com.github.loyaltycardwallet.services.StampCardService;
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
class StampCardControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private StampCardService stampCardService;

    @InjectMocks
    private StampCardController stampCardController;

    private JacksonTester<StampCard> jsonStampCard;

    private StampCard stampCard1;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(stampCardController).build();

        stampCard1 = StampCard
                .builder()
                .score(10)
                .stampCardReward(null)
                .build();
    }


    @Test
    void testDoesMethodGetAllStampCardsReturnsStatusCode200() throws Exception {
        List<StampCard> userList = new ArrayList<>(Arrays.asList(stampCard1));

        BDDMockito.when(stampCardService.findAll())
                .thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stampcards").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDoesMethodGetStampCardByIdReturnStatusCode404() throws Exception {
        BDDMockito.when(stampCardService.findById(BDDMockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stampcards/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void testDoesMethodAddStampCardReturnsStatusCode202() throws Exception {
        BDDMockito.when(stampCardService.save(BDDMockito.any()))
                .thenReturn(stampCard1);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/stampcards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStampCard.write(stampCard1).getJson()))
                .andReturn().getResponse();

        log.warn(response.getContentAsString());


        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testDoesMethodUpdateStampCardReturnsStatusCode404() throws Exception {

        BDDMockito.when(stampCardService.existById(BDDMockito.any()))
                .thenReturn(false);

        BDDMockito.when(stampCardService.save(BDDMockito.any()))
                .thenReturn(stampCard1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/stampcards/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStampCard.write(stampCard1).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDoesMethodUpdateStampCardReturnsStatusCode204() throws Exception {
        BDDMockito.when(stampCardService.existById(BDDMockito.any()))
                .thenReturn(true);

        BDDMockito.when(stampCardService.save(BDDMockito.any()))
                .thenReturn(stampCard1);

        stampCard1.setScore(12);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/stampcards/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStampCard.write(stampCard1).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteStampCardByIdReturnsStatusCode204() throws Exception {
        BDDMockito.when(stampCardService.existById(BDDMockito.any()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/stampcards/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteStampCardByIdReturnsStatusCode404() throws Exception {
        BDDMockito.when(stampCardService.existById(BDDMockito.any()))
                .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/stampcards/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }


}
