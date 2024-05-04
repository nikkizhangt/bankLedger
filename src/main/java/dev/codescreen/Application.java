package dev.codescreen;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Dummy class with a dummy method.
 * Rename this class and method to a name that is more appropriate to your coding test.
 */
@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
  /**
   *
   *
   * @return
   */
}