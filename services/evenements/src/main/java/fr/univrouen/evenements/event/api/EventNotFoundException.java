package fr.univrouen.evenements.event.api;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(Integer id) {
        super("Event " + id + " not found");
    }
}
