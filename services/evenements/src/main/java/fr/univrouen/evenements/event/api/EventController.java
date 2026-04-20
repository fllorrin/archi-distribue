package fr.univrouen.evenements.event.api;

import fr.univrouen.evenements.event.service.EventService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventResponse> findAll(
            @RequestParam(name = "vehicle_id", required = false) Integer vehicleId,
            @RequestParam(name = "event", required = false) String eventType
    ) {
        return eventService.findAll(vehicleId, eventType);
    }

    @GetMapping("/{id}")
    public EventResponse findById(@PathVariable Integer id) {
        return eventService.findById(id);
    }

    @PostMapping
    public ResponseEntity<EventResponse> create(@Valid @RequestBody EventInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.create(input));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
