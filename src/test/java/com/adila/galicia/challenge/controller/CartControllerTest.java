package com.adila.galicia.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.adila.galicia.challenge.dto.request.CreateCartRequest;
import com.adila.galicia.challenge.dto.request.GetCartsByClientRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CartControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;


  @Test
  @WithMockUser(username = "test_user1")
  void createCartOK() throws Exception {
    CreateCartRequest request = new CreateCartRequest();
    request.setUserId(1L);

    mockMvc.perform(post("/carritos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.carrito_id").exists())
        .andExpect(jsonPath("$.usuario_id").exists())
        .andExpect(jsonPath("$.estado").value("OPEN"));
  }

  @Test
  @WithMockUser(username = "otherUser")
  void createCartInvalidUser() throws Exception {
    CreateCartRequest request = new CreateCartRequest();
    request.setUserId(1L);

    mockMvc.perform(post("/carritos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username = "test_user1")
  void getCartByClientOk() throws Exception {
    GetCartsByClientRequest request = new GetCartsByClientRequest();
    request.setUserId(1L);

    mockMvc.perform(get("/carritos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "test_user1")
  void processCartOK() throws Exception {
    CreateCartRequest request = new CreateCartRequest();
    request.setUserId(1L);

    mockMvc.perform(post("/carritos/1/procesar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isAccepted());
  }
}