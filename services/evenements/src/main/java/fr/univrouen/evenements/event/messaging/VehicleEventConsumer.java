package fr.univrouen.evenements.event.messaging;

import fr.univrouen.evenements.event.service.EventService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class VehicleEventConsumer {

    private final EventService eventService;

    public VehicleEventConsumer(EventService eventService) {
        this.eventService = eventService;
    }

    @KafkaListener(
            topics = "${app.kafka.topic.vehicle:vehicule_topic}",
            groupId = "${app.kafka.group-id:evenements-service}",
            autoStartup = "${app.kafka.enabled:true}"
    )
    public void onVehicleEvent(VehicleEvent event) {
        if (event == null) {
            return;
        }

        eventService.createFromKafka(
                event.vehicle_id(),
                event.event(),
                event.timestamp(),
                event.event_id()
        );
    }
}
