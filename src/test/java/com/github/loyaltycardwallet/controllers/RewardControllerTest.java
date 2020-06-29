package com.github.loyaltycardwallet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.loyaltycardwallet.models.Reward;
import com.github.loyaltycardwallet.services.RewardService;
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
class RewardControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @InjectMocks
    private RewardController rewardController;

    private JacksonTester<Reward> jsonReward;

    private Reward reward1;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(rewardController).build();

        reward1 = Reward
                .builder()
                .rewardDescription("SOMETHING COOL MAN")
                .build();
    }


    @Test
    void testDoesMethodGetAllRewardsReturnsStatusCode200() throws Exception {
        List<Reward> rewardList = new ArrayList<>(Arrays.asList(reward1));

        BDDMockito.when(rewardService.findAll())
                .thenReturn(rewardList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rewards").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDoesMethodGetRewardByIdReturnStatusCode404() throws Exception {
        BDDMockito.when(rewardService.findById(BDDMockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rewards/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void testDoesMethodAddRewardReturnsStatusCode202() throws Exception {
        BDDMockito.when(rewardService.save(BDDMockito.any()))
                .thenReturn(reward1);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/rewards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReward.write(reward1).getJson()))
                .andReturn().getResponse();

        log.warn(response.getContentAsString());


        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testDoesMethodUpdateRewardReturnsStatusCode404() throws Exception {

        BDDMockito.when(rewardService.existById(BDDMockito.any()))
                .thenReturn(false);

        BDDMockito.when(rewardService.save(BDDMockito.any()))
                .thenReturn(reward1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/rewards/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReward.write(reward1).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDoesMethodUpdateRewardReturnsStatusCode204() throws Exception {
        BDDMockito.when(rewardService.existById(BDDMockito.any()))
                .thenReturn(true);

        BDDMockito.when(rewardService.save(BDDMockito.any()))
                .thenReturn(reward1);

        reward1.setRewardDescription("Kreward1");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/rewards/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReward.write(reward1).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteRewardByIdReturnsStatusCode204() throws Exception {
        BDDMockito.when(rewardService.existById(BDDMockito.any()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/rewards/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteRewardByIdReturnsStatusCode404() throws Exception {
        BDDMockito.when(rewardService.existById(BDDMockito.any()))
                .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/rewards/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

}
