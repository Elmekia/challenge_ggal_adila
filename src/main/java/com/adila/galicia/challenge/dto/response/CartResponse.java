package com.adila.galicia.challenge.dto.response;

import com.adila.galicia.challenge.utils.CartStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponse {

  @JsonProperty("carrito_id")
  private Long id;
  @JsonProperty("usuario_id")
  private Long userId;
  @JsonProperty("productos")
  private List<ItemResponse> items;
  @JsonProperty("estado")
  private CartStatus status;
  private BigDecimal total;
}
