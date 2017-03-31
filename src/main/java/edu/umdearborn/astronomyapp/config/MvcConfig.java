package edu.umdearborn.astronomyapp.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.umdearborn.astronomyapp.config.sanitiser.SanatizedStringSeralizer;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

  private static final Logger logger = LoggerFactory.getLogger(MvcConfig.class);

  @Value("${server.cors.origin:*}")
  private String corsOrigin;

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index.html");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    logger.info("Allowed CORS origin: '{}'", corsOrigin);
    registry.addMapping("*").allowedOrigins(corsOrigin);
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(jsonConverter());
  }

  @Bean
  public HttpMessageConverter<Object> jsonConverter() {
    ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
        .serializerByType(String.class, new SanatizedStringSeralizer()).build();
    MappingJackson2HttpMessageConverter httpMessageConverter =
        new MappingJackson2HttpMessageConverter(objectMapper);
    httpMessageConverter.setPrefixJson(false);

    return httpMessageConverter;
  }

}
