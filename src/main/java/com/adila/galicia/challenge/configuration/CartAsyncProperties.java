package com.adila.galicia.challenge.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "async.cart")
@Getter
@Setter
public class CartAsyncProperties {

  private int corePoolSize;
  private int maxPoolSize;
  private int queueCapacity;
  private String threadNamePrefix;
}