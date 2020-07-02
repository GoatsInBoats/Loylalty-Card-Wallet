package com.github.loyaltycardwallet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.loyaltycardwallet.models.Company;
import com.github.loyaltycardwallet.services.CompanyService;
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
class CompanyControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private JacksonTester<Company> jsonCompany;

    private Company commpany1;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();

        commpany1 = Company
                .builder()
                .companyName("Great company")
                .city("Krakow")
                .zipCode("12-345")
                .street("Florianska")
                .localNumber(17)
                .latitude("50.062938")
                .longitude("19.939988")
                .stampCard(null)
                .build();
    }


    @Test
    void testDoesMethodGetAllCompaniesReturnsStatusCode200() throws Exception {
        List<Company> companies = new ArrayList<>(Arrays.asList(commpany1));

        BDDMockito.when(companyService.findAll())
                .thenReturn(companies);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/companies").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDoesMethodGetCompanyByIdReturnStatusCode404() throws Exception {
        BDDMockito.when(companyService.findById(BDDMockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/companies/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void testDoesMethodAddCompanyReturnsStatusCode202() throws Exception {
        BDDMockito.when(companyService.save(BDDMockito.any()))
                .thenReturn(commpany1);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCompany.write(commpany1).getJson()))
                .andReturn().getResponse();

        log.warn(response.getContentAsString());


        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void testDoesMethodUpdateCompanyReturnsStatusCode404() throws Exception {

        BDDMockito.when(companyService.existById(BDDMockito.any()))
                .thenReturn(false);

        BDDMockito.when(companyService.save(BDDMockito.any()))
                .thenReturn(commpany1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/companies/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCompany.write(commpany1).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDoesMethodUpdateCompanyReturnsStatusCode204() throws Exception {
        BDDMockito.when(companyService.existById(BDDMockito.any()))
                .thenReturn(true);

        BDDMockito.when(companyService.save(BDDMockito.any()))
                .thenReturn(commpany1);

        commpany1.setCompanyName("Kcommpany1");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/companies/ae737b90-a25b-46b1-8b3d-e6407cf726c2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCompany.write(commpany1).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteCompanyByIdReturnsStatusCode204() throws Exception {
        BDDMockito.when(companyService.existById(BDDMockito.any()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/companies/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void testDoesMethodDeleteCompanyByIdReturnsStatusCode404() throws Exception {
        BDDMockito.when(companyService.existById(BDDMockito.any()))
                .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/companies/ae737b90-a25b-46b1-8b3d-e6407cf726c2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

}
