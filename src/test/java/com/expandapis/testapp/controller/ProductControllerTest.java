package com.expandapis.testapp.controller;

import com.expandapis.testapp.service.ProductService;
import com.expandapis.testapp.util.AppConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @WithMockUser
    void testAddProducts() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put(AppConstants.JSON_TABLE_KEY, "products");
        payload.put(AppConstants.JSON_RECORD_KEY, List.of("entryDate","03-01-2023"));

        when(productService.productHandler(any())).thenReturn(true);

        mockMvc.perform(post("/api/v1/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(
                        payload.get(AppConstants.JSON_RECORD_KEY) + " was successfully added")));

        verify(productService, times(1)).productHandler(any());
    }

    @Test
    @WithMockUser
    void testGetAllProducts() throws Exception {
        List<Map<String, Object>> products = new ArrayList<>();
        products.add(new HashMap<>());
        products.add(new HashMap<>());

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/v1/products/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(productService, times(1)).getAllProducts();
    }
}
