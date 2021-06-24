package cyb.oop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cyb.oop.converter.PeriodicalConverter;
import cyb.oop.dto.PeriodicalDTO;
import cyb.oop.entity.Periodical;
import cyb.oop.repository.PeriodicalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PeriodicalService {
    private final PeriodicalRepository periodicalRepository;
    private final PeriodicalConverter periodicalConverter;

    @Autowired
    public PeriodicalService(PeriodicalRepository periodicalRepository, PeriodicalConverter periodicalConverter) {
        this.periodicalRepository = periodicalRepository;
        this.periodicalConverter = periodicalConverter;
    }

    public List<Periodical> findAll() {
        return periodicalRepository.findAll();
    }

    public Optional<Periodical> findById(long id) {
        return periodicalRepository.findById(id);
    }

    public List<Periodical> findByName(String name) {
        return periodicalRepository.findByName(name);
    }

    @Transactional
    public void changePeriodicalAvailability(long id, boolean available) {
        Optional<Periodical> periodical = findById(id);
        periodical.ifPresent(value -> {
            value.setAvailable(available);
            periodicalRepository.save(value);
        });
    }
}
