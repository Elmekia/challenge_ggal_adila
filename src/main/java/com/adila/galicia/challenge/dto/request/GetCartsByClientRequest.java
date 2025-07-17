package com.adila.galicia.challenge.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetCartsByClientRequest {

  @NotNull
  @Positive
  private Long userId;
}
