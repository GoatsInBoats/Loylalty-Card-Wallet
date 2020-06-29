package com.github.loyaltycardwallet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.loyaltycardwallet.models.Reward;
import com.github.loyaltycardwallet.models.StampCardReward;
import com.github.loyaltycardwallet.services.StampCardRewardService;
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
class StampCardRewardControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private StampCardRewardService stampCardRewardService;

    @InjectMocks
    private StampCardRewardController stampCardRewardController;

    private JacksonTester<StampCardReward> jsonStampCardReward;

    private StampCardReward stampCardRewardTest1;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(stampCardRewardController).build();
        Reward reward1 = Reward
                .builder()
                .rewardDescription("SOMETHING COOL MAN")
                .build();

        stampCardRewardTest1 = StampCardReward
                .builder()
                .position(10)
                .reward(reward1)
                .build();
    }


    @Test
    void testDoesMethodGetAllStampCardRewardsReturnsStatusCode200() throws Exception {
        List<StampCardReward> stampCardRewardList = new ArrayList<>(Arrays.asList(stampCardRewardTest1));

        BDDMockito.when(stampCardRewardService.findAll())
                .thenReturn(stampCardRewardList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stampcards-rewards").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDoesMethodGetStampCardRewardByIdReturnStatusCode404() throws Exception {
        BDDMockito.when(stampCardRewardService.findById(BDDMockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stampcards-rewards/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void testDoesMethodAddStampCardRewardReturnsStatusCode202() throws Exception {
        BDDMockito.when(stampCardRewardService.save(BDDMockito.any()))
                .thenReturn(stampCardRewardTest1);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/stampcards-rewards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStampCardReward.write(stampCardRewardTest1).getJson()))
                .andReturn().getResponse();

        log.warn(response.getContentAsString());


        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testDoesMethodUpdateStampCardRewardReturnsStatusCode404() throws Exception {

        BDDMockito.when(stampCardRewardService.existById(BDDMockito.any()))
                .thenReturn(false);

        BDDMockito.when(stampCardRewardService.save(BDDMockito.any()))
                .thenReturn(stampCardRewardTest1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/stampcards-rewards/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStampCardReward.write(stampCardRewardTest1).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDoesMethodUpdateStampCardRewardReturnsStatusCode204() throws Exception {
        BDDMockito.when(stampCardRewardService.existById(BDDMockito.any()))
                .thenReturn(true);

        BDDMockito.when(stampCardRewardService.save(BDDMockito.any()))
                .thenReturn(stampCardRewardTest1);

        stampCardRewardTest1.setPosition(2);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/stampcards-rewards/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStampCardReward.write(stampCardRewardTest1).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteStampCardRewardByIdReturnsStatusCode204() throws Exception {
        BDDMockito.when(stampCardRewardService.existById(BDDMockito.any()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/stampcards-rewards/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteStampCardRewardByIdReturnsStatusCode404() throws Exception {
        BDDMockito.when(stampCardRewardService.existById(BDDMockito.any()))
                .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/stampcards-rewards/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

}
