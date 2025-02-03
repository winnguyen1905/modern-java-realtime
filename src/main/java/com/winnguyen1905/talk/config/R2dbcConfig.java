package com.winnguyen1905.talk.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
@EnableR2dbcAuditing
@EnableR2dbcRepositories
public class R2dbcConfig extends AbstractR2dbcConfiguration {
  @Value("${spring.r2dbc.host}")
  private String host;

  @Value("${spring.r2dbc.port}")
  private Integer port;

  @Value("${spring.r2dbc.password}")
  private String password;

  @Value("${spring.r2dbc.database}")
  private String database;

  @Value("${spring.r2dbc.username}")
  private String username;

  @Bean
  @Override
  public ConnectionFactory connectionFactory() {
    return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
        .host(host)
        .port(port)
        .username(username)
        .database(database)
        .password(password)
        .build());
  }

  @Bean
  public ReactiveTransactionManager reactiveTransactionManager() {
    return new R2dbcTransactionManager(connectionFactory());
  }

  @Bean
  public TransactionalOperator transactionalOperator() {
    return TransactionalOperator.create(reactiveTransactionManager());
  }

  @Bean
  public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
    return new R2dbcEntityTemplate(connectionFactory);
  }
}
