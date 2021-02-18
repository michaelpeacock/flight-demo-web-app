package io.confluent.flightdemo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import io.confluent.flightdemo.models.DashboardModel;
import io.confluent.flightdemo.models.FlightModel;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    public ConsumerFactory<String, String> consumerFactory(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(groupId));
        return factory;
    }

    public ConsumerFactory<String, DashboardModel> dashboardConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "dashboard");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), 
            new JsonDeserializer<>(DashboardModel.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DashboardModel> dashboardKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DashboardModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(dashboardConsumerFactory());
        return factory;
    }

    public ConsumerFactory<String, FlightModel> flightConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "flight");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), 
            new JsonDeserializer<>(FlightModel.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FlightModel> flightKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FlightModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(flightConsumerFactory());
        return factory;
    }
}
