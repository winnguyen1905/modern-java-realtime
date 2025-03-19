package com.winnguyen1905.talk.notification.config;

import com.rabbitmq.client.AMQP.Queue.BindOk;
import com.rabbitmq.client.ConnectionFactory;

import reactor.rabbitmq.Sender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

@Configuration
public class NotificationProcessingConfiguration {

  public static final String NOTI_QUEUE_NAME = "notification-queue";
  public static final String NOTI_DIRECT_EXCHANGE = "notification-exchange";
  public static final String NOTI_ROUTING_KEY = "notification-routing-key";

  @Bean
  public ConnectionFactory connectionFactory() {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    factory.setPort(5672);
    factory.setUsername("guest");
    factory.setPassword("guest");
    return factory;
  }

  @Bean
  public Sender sender(ConnectionFactory connectionFactory) {
    return RabbitFlux.createSender(new SenderOptions().connectionFactory(connectionFactory));
  }

  @Bean
  public Receiver receiver(ConnectionFactory connectionFactory) {
    return RabbitFlux.createReceiver(new ReceiverOptions().connectionFactory(connectionFactory));
  }

  @Bean
  public Mono<BindOk> setupQueues(Sender sender) {
    return sender.declareExchange(ExchangeSpecification.exchange(NOTI_DIRECT_EXCHANGE).type("direct"))
        .then(sender.declareQueue(QueueSpecification.queue(NOTI_QUEUE_NAME)))
        .then(sender.bindQueue(BindingSpecification.binding(NOTI_DIRECT_EXCHANGE, NOTI_ROUTING_KEY, NOTI_QUEUE_NAME)));
  }
}
