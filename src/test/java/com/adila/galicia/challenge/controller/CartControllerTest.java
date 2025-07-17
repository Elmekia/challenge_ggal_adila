package com.adila.galicia.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.adila.galicia.challenge.dto.request.CreateCartRequest;
import com.adila.galicia.challenge.dto.request.GetCartsByClientRequest;
import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.CartItem;
import com.adila.galicia.challenge.entity.Product;
import com.adila.galicia.challenge.entity.User;
import com.adila.galicia.challenge.repository.CartItemRepository;
import com.adila.galicia.challenge.repository.CartRepository;
import com.adila.galicia.challenge.repository.UserRepository;
import com.adila.galicia.challenge.utils.CartStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private CartItemRepository cartItemRepository;

  @BeforeAll
  void setUpOnce() {
    this.userRepository.deleteAll();
    User user = User.builder().name("testerUser").role("user").password("asd").build();
    this.userRepository.save(user);
    this.cartRepository.save(Cart.builder().user(user).status(CartStatus.OPEN).build());
    this.cartItemRepository.
        save(CartItem.builder().cart(Cart.builder().id(1L).build())
            .product(Product.builder().id(1L).name("algo").price(
                BigDecimal.valueOf(100)).stock(100).build()).numberOfItems(2).build());
  }

  @Test
  @WithMockUser(username = "testerUser")
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
  @WithMockUser(username = "testerUser")
  void getCartByClientOk() throws Exception {
    GetCartsByClientRequest request = new GetCartsByClientRequest();
    request.setUserId(1L);

    mockMvc.perform(get("/carritos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "testerUser")
  void processCartOK() throws Exception {
    CreateCartRequest request = new CreateCartRequest();
    request.setUserId(1L);

    mockMvc.perform(post("/carritos/1/procesar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isAccepted());
  }
}