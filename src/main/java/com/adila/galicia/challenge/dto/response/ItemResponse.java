package com.adila.galicia.challenge.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemResponse {

  @JsonProperty("producto_id")
  private Long id;
  @JsonProperty("nombre")
  private String name;
  @JsonProperty("nombre_categoria")
  private String categoryName;
  @JsonProperty("precio")
  private BigDecimal price;
  @JsonProperty("cantidad")
  private Integer numberOfItems;

}
