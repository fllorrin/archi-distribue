package fr.univrouen.evenements.event.graphql;

import fr.univrouen.evenements.event.api.EventInput;
import fr.univrouen.evenements.event.api.EventResponse;
import fr.univrouen.evenements.event.service.EventService;
import java.time.Instant;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class EventGraphqlResolver {

    private final EventService eventService;

    public EventGraphqlResolver(EventService eventService) {
        this.eventService = eventService;
    }

    @QueryMapping
    public List<EventResponse> events(@Argument Integer vehicle_id, @Argument String event) {
        return eventService.findAll(vehicle_id, event);
    }

    @QueryMapping
    public EventResponse event(@Argument Integer id) {
        return eventService.findById(id);
    }

    @MutationMapping
    public EventResponse createEvent(
            @Argument Integer vehicle_id,
            @Argument String time,
            @Argument String event
    ) {
        EventInput input = new EventInput(vehicle_id, Instant.parse(time), event);
        return eventService.create(input);
    }

    @MutationMapping
    public Boolean deleteEvent(@Argument Integer id) {
        eventService.delete(id);
        return true;
    }
}
