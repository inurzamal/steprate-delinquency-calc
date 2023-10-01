package com.nur.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nur.model.SearchRequest;
import com.nur.service.BookService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController bookController;
    @Mock
    private BookService bookService;

    @Test
    void mongoSearchBookTest() throws Exception {
        SearchRequest searchRequest = Instancio.create(SearchRequest.class);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/mongoSearch")
                .content(asJsonString(searchRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void mongoSearchBookTestWhenexception() throws Exception {
        SearchRequest searchRequest = Instancio.create(SearchRequest.class);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        doThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/mongoSearch")
                        .content(asJsonString(searchRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }

}