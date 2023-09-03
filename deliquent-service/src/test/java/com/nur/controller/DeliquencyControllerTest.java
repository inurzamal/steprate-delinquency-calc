package com.nur.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nur.model.DelinquencyRequest;
import com.nur.service.DelinquencyCalcService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DeliquencyControllerTest {

    @Mock
    DelinquencyCalcService delinquencyCalcService;
    @InjectMocks
    private DeliquencyController deliquencyController;

    @Test
    void delinquencyCalculationTest() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(deliquencyController).build();
        doReturn(new ArrayList<>()).when(delinquencyCalcService).getDelinquencyMonthlyRecords(any());
        DelinquencyRequest request = createRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/steprate/delinquency/v1/calculate")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
    void delinquencyCalculationWhenExceptionTest() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(deliquencyController).build();
        doThrow(new RuntimeException());
        DelinquencyRequest request = createRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/steprate/delinquency/v1/calculate")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }

    private DelinquencyRequest createRequest() {
        return Instancio.create(DelinquencyRequest.class);
    }

}