package com.adila.galicia.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.adila.galicia.challenge.dto.request.AddProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class CartItemControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  @Order(1)
  @WithMockUser(username = "testerUser")
  void addProductToCartOK() throws Exception {
    AddProductRequest request = new AddProductRequest();
    request.setProductId(1L);
    request.setNumberOfItems(2);

    mockMvc.perform(post("/carritos/2/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.carrito_id").exists())
        .andExpect(jsonPath("$.productos").isArray());
  }

  @Test
  @Order(2)
  @WithMockUser(username = "test_user1")
  void getProductsFromCartOK() throws Exception {
    mockMvc.perform(get("/carritos/2/productos")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  @Order(3)
  @WithMockUser(username = "test_user1")
  void deleteProductFromCartOK() throws Exception {
    mockMvc.perform(delete("/carritos/2/productos/1"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(4)
  @WithMockUser(username = "test_user1")
  void getProductsFromCartNotFound() throws Exception {
    mockMvc.perform(get("/carritos/10/productos")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}