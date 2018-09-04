package com.juanroldanbrz.examples.banktransfer.configuration;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.Mapper;
import org.jongo.marshall.jackson.JacksonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JongoConfiguration {

  /**
   * Instance of Jongo
   * @param mongoClient client of mongoDB
   * @return a instance of Jongo
   */
  @Bean
  public Jongo jongo(final MongoClient mongoClient) {
    return new Jongo(mongoClient.getDB("bank_db"), mapper());
  }

  /**
   * In order to use the Java 8 time utils (like Instant) we need to create our custom mapper
   * @return the mapper
   */
  private Mapper mapper() {
    return new JacksonMapper.Builder()
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .registerModule(new JavaTimeModule())
        .build();
  }
}
