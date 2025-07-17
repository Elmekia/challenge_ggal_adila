package com.adila.galicia.challenge.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddProductRequest {

  @NotNull
  @Positive
  private Long productId;
  @NotNull
  @Positive
  @JsonProperty("cantidad")
  private Integer numberOfItems;

}
