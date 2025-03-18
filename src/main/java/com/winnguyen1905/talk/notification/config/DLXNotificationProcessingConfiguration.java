package com.winnguyen1905.talk.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.AMQP.Queue.BindOk;

import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

@Configuration
public class DLXNotificationProcessingConfiguration {

  public static final String NOTI_DL_QUEUE_NAME = "DLQueue";
  public static final String NOTI_DL_EXCHANGE = "DLExchange";
  public static final String NOTI_DL_ROUTING_KEY = "routingkey";

  @Bean
  Mono<BindOk> setupDLXQueues(Sender sender) {
    return sender.declareExchange(ExchangeSpecification.exchange(NOTI_DL_EXCHANGE).type("direct"))
        .then(sender.declareQueue(QueueSpecification.queue(NOTI_DL_QUEUE_NAME)))
        .then(
            sender.bindQueue(BindingSpecification.binding(NOTI_DL_EXCHANGE, NOTI_DL_ROUTING_KEY, NOTI_DL_QUEUE_NAME)));
  }
}
