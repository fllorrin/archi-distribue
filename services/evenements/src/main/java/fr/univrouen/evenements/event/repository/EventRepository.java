package fr.univrouen.evenements.event.repository;

import fr.univrouen.evenements.event.domain.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findAllByOrderByTimeDescIdDesc();

    List<Event> findByVehicleIdOrderByTimeDescIdDesc(Integer vehicleId);

    List<Event> findByEventOrderByTimeDescIdDesc(String event);

    List<Event> findByVehicleIdAndEventOrderByTimeDescIdDesc(Integer vehicleId, String event);

    boolean existsByExternalEventId(String externalEventId);
}
