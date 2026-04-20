package fr.univrouen.evenements.event.service;

import fr.univrouen.evenements.event.api.EventInput;
import fr.univrouen.evenements.event.api.EventNotFoundException;
import fr.univrouen.evenements.event.api.EventResponse;
import fr.univrouen.evenements.event.domain.Event;
import fr.univrouen.evenements.event.repository.EventRepository;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventResponse> findAll(Integer vehicleId, String eventType) {
        String normalizedEvent = normalizeEvent(eventType);
        List<Event> events;

        if (vehicleId != null && hasText(normalizedEvent)) {
            events = eventRepository.findByVehicleIdAndEventOrderByTimeDescIdDesc(vehicleId, normalizedEvent);
        } else if (vehicleId != null) {
            events = eventRepository.findByVehicleIdOrderByTimeDescIdDesc(vehicleId);
        } else if (hasText(normalizedEvent)) {
            events = eventRepository.findByEventOrderByTimeDescIdDesc(normalizedEvent);
        } else {
            events = eventRepository.findAllByOrderByTimeDescIdDesc();
        }

        return events.stream().map(this::toResponse).toList();
    }

    public EventResponse findById(Integer id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return toResponse(event);
    }

    public EventResponse create(EventInput input) {
        Event event = new Event();
        event.setVehicleId(input.vehicle_id());
        event.setTime(input.time());
        event.setEvent(normalizeEvent(input.event()));
        return toResponse(eventRepository.save(event));
    }

    public void delete(Integer id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException(id);
        }
        eventRepository.deleteById(id);
    }

    @Transactional
    public void createFromKafka(Integer vehicleId, String eventType, Instant timestamp, String externalEventId) {
        if (vehicleId == null || !hasText(eventType) || timestamp == null) {
            return;
        }

        if (hasText(externalEventId) && eventRepository.existsByExternalEventId(externalEventId)) {
            return;
        }

        Event event = new Event();
        event.setVehicleId(vehicleId);
        event.setTime(timestamp);
        event.setEvent(normalizeEvent(eventType));
        if (hasText(externalEventId)) {
            event.setExternalEventId(externalEventId);
        }

        eventRepository.save(event);
    }

    private EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getVehicleId(),
                event.getTime(),
                event.getEvent()
        );
    }

    private String normalizeEvent(String value) {
        return value == null ? null : value.trim().toUpperCase(Locale.ROOT);
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
