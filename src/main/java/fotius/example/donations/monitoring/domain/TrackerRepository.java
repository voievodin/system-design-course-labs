package fotius.example.donations.monitoring.domain;

import fotius.example.donations.monitoring.domain.model.Tracker;

import java.util.Optional;

public interface TrackerRepository {
    Optional<Tracker> findById(long id);
    Optional<Tracker[]> findAll();
    void insert(Tracker tracker);
}