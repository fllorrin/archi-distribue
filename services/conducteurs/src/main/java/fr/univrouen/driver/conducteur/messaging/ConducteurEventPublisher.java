package fr.univrouen.driver.conducteur.messaging;

import fr.univrouen.driver.conducteur.api.ConducteurResponse;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConducteurEventPublisher {

    private final KafkaTemplate<Object, Object> kafkaTemplate;
    private final String conducteurTopic;
    private final boolean kafkaEnabled;

    @Autowired
    public ConducteurEventPublisher(
            ObjectProvider<KafkaTemplate<Object, Object>> kafkaTemplateProvider,
            @Value("${app.kafka.topic.conducteur:conducteur_topic}") String conducteurTopic,
            @Value("${app.kafka.enabled:true}") boolean kafkaEnabled
    ) {
        this(kafkaTemplateProvider.getIfAvailable(), conducteurTopic, kafkaEnabled);
    }

    ConducteurEventPublisher(KafkaTemplate<Object, Object> kafkaTemplate, String conducteurTopic, boolean kafkaEnabled) {
        this.kafkaTemplate = kafkaTemplate;
        this.conducteurTopic = conducteurTopic;
        this.kafkaEnabled = kafkaEnabled;
    }

    public void publishCreated(ConducteurResponse conducteur) {
        publish(conducteur, "CONDUCTEUR_CREATED");
    }

    public void publishUpdated(ConducteurResponse conducteur) {
        publish(conducteur, "CONDUCTEUR_UPDATED");
    }

    public void publishDeleted(Integer conducteurId) {
        if (!kafkaEnabled || kafkaTemplate == null) {
            return;
        }

        ConducteurEvent event = new ConducteurEvent(
                conducteurId,
                null,
                null,
                "CONDUCTEUR_DELETED",
                Instant.now(),
                "1.0"
        );

        kafkaTemplate.send(conducteurTopic, String.valueOf(conducteurId), event);
    }

    private void publish(ConducteurResponse conducteur, String eventType) {
        if (!kafkaEnabled || kafkaTemplate == null) {
            return;
        }

        ConducteurEvent event = new ConducteurEvent(
                conducteur.id(),
                conducteur.prenom(),
                conducteur.nom(),
                eventType,
                Instant.now(),
                "1.0"
        );

        kafkaTemplate.send(conducteurTopic, String.valueOf(conducteur.id()), event);
    }
}
