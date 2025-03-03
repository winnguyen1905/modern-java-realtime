package com.winnguyen1905.talk.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.winnguyen1905.talk.rest.gateway.CallSocketHandler;
import com.winnguyen1905.talk.rest.gateway.ChatSocketHandler;
import com.corundumstudio.socketio.SocketIONamespace;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class NettySocketIOConfig {

  @Bean
  SocketIOServer socketIOServer() {
    Configuration config = new Configuration();
    config.setHostname("localhost");
    config.setPort(9092);
    return new SocketIOServer(config);
  }

  @Bean
  SocketIONamespace chatNamespace(SocketIOServer server, ChatSocketHandler chatHandler) {
    SocketIONamespace namespace = server.addNamespace("/chat");
    return namespace;
  }

  @Bean
  SocketIONamespace callNamespace(SocketIOServer server, CallSocketHandler callHandler) {
    SocketIONamespace namespace = server.addNamespace("/call");
    return namespace;
  }
}
