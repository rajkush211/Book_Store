package com.bridgelabz.bookstoreapp.controllertest;

import com.bridgelabz.bookstoreapp.controller.AdminController;
import com.bridgelabz.bookstoreapp.dto.BookDto;
import com.bridgelabz.bookstoreapp.service.IBookStoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.junit.Assert;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @MockBean
    IBookStoreService iBookStoreService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void whenLoadCsv_shouldReturn_DataLoadedSuccessfully() throws Exception {
        String value = "CSV loaded successfully";
        when(iBookStoreService.loadBookData()).thenReturn(value);
        MvcResult mvcResult = this.mockMvc.perform(get("/home/admin/loadcsv")).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(value, result);
    }

    @Test
    void whenGiveBookDto_ShouldReturnBookAdded() throws Exception {
        BookDto bookDto = new BookDto("Rajkush", "Harry Potter", "http:/www.harrypotter.com/pic", 1750, "Harry Potter description");
        String convertToJson = objectMapper.writeValueAsString(bookDto);
        when(iBookStoreService.addNewBook(any())).thenReturn(convertToJson);
        MvcResult mvcResult = this.mockMvc.perform(post("/home/admin/addbook")
                                          .content(convertToJson)
                                          .contentType(MediaType.APPLICATION_JSON))
                                          .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(result);
        BookDto values = objectMapper.readValue(result, BookDto.class);
        Assert.assertEquals("Rajkush", values.getAuthor());
    }
}
