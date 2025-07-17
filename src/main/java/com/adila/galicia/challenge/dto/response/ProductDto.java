package com.adila.galicia.challenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDto {

  private Long id;
  private String name;
  private Long categoryId;
}
