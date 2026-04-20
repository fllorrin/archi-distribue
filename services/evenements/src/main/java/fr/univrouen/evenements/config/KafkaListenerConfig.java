package fr.univrouen.evenements.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

@Configuration
@EnableKafka
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class KafkaListenerConfig {

    @Bean
    @ConditionalOnMissingBean(ConsumerFactory.class)
    ConsumerFactory<Object, Object> fallbackConsumerFactory(
            @Value("${spring.kafka.bootstrap-servers:localhost:29092}") String bootstrapServers,
            @Value("${spring.kafka.consumer.group-id:evenements-service}") String groupId,
            @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages:fr.univrouen.evenements.event.messaging}")
            String trustedPackages,
            @Value("${spring.kafka.consumer.properties.spring.json.value.default.type:fr.univrouen.evenements.event.messaging.VehicleEvent}")
            String defaultValueType,
            @Value("${spring.kafka.consumer.properties.spring.json.use.type.headers:false}") boolean useTypeHeaders
    ) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        props.put("spring.json.trusted.packages", trustedPackages);
        props.put("spring.json.value.default.type", defaultValueType);
        props.put("spring.json.use.type.headers", useTypeHeaders);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "kafkaListenerContainerFactory")
    @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
    ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
            ConsumerFactory<Object, Object> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
