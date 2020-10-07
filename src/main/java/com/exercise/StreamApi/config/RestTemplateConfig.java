package com.exercise.StreamApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Bean("clientRest")
  public RestTemplate registerRestTemplate() {
    return new RestTemplate();
  }
}
