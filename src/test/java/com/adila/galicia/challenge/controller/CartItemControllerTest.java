package com.adila.galicia.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.adila.galicia.challenge.dto.request.AddProductRequest;
import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.Product;
import com.adila.galicia.challenge.entity.User;
import com.adila.galicia.challenge.repository.CartItemRepository;
import com.adila.galicia.challenge.repository.CartRepository;
import com.adila.galicia.challenge.repository.UserRepository;
import com.adila.galicia.challenge.utils.CartStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartItemControllerTest {

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
  void setup() {
    this.userRepository.deleteAll();
    this.cartRepository.deleteAll();
    this.cartItemRepository.deleteAll();

    User user = User.builder().name("testerUser").role("user").password("asd").build();
    userRepository.save(user);

    Product product = Product.builder().id(1L).name("Producto Test").price(BigDecimal.valueOf(50))
        .stock(100).build();
    // persistilo si tenés repositorio o simulalo con los datos fijos

    Cart cart = Cart.builder().user(user).status(CartStatus.OPEN).build();
    cartRepository.save(cart);
  }

  @Test
  @Order(1)
  @WithMockUser(username = "testerUser")
  void addProductToCartOK() throws Exception {
    AddProductRequest request = new AddProductRequest();
    request.setProductId(1L);
    request.setNumberOfItems(2);

    mockMvc.perform(post("/carritos/1/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.carrito_id").exists())
        .andExpect(jsonPath("$.productos").isArray());
  }

  @Test
  @Order(2)
  @WithMockUser(username = "testerUser")
  void getProductsFromCartOK() throws Exception {
    mockMvc.perform(get("/carritos/1/productos")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  @Order(3)
  @WithMockUser(username = "testerUser")
  void deleteProductFromCartOK() throws Exception {
    mockMvc.perform(delete("/carritos/1/productos/1"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(4)
  @WithMockUser(username = "testerUser")
  void getProductsFromCartNotFound() throws Exception {
    mockMvc.perform(get("/carritos/10/productos")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}