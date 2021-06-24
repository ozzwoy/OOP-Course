package cyb.oop.repository;

import java.util.List;
import java.util.Optional;

import cyb.oop.entity.Periodical;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodicalRepository extends JpaRepository<Periodical, Long> {

    List<Periodical> findByName(String name);
}
