package com.winnguyen1905.talk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.corundumstudio.socketio.SocketIOServer;

import jakarta.annotation.PreDestroy;

@CrossOrigin
@Configuration
public class SocketIOConfig {

  @Value("${socket-server.host}")
  private String SOCKET_HOST;

  @Value("${socket-server.port}")
  private int SOCKET_PORT;

  private SocketIOServer server;

  @Bean
  public SocketIOServer socketIOServer() {
    com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
    config.setHostname(SOCKET_HOST);
    config.setPort(SOCKET_PORT);
    server = new SocketIOServer(config);
    server.start();

    return server;
  }

  @PreDestroy
  public void stopSocketIOServer() {
    this.server.stop();
  }

}
