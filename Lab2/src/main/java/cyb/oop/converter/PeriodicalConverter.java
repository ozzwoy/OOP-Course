package cyb.oop.converter;

import cyb.oop.dto.PeriodicalDTO;
import cyb.oop.entity.Periodical;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PeriodicalConverter {

    public PeriodicalDTO toDTO(Periodical entity) {
        PeriodicalDTO dto = new PeriodicalDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setPeriod(entity.getPeriod());
        dto.setAvailable(entity.isAvailable());

        return dto;
    }
    
    public Periodical toEntity(PeriodicalDTO dto) {
        Periodical entity = new Periodical();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setPeriod(dto.getPeriod());
        entity.setAvailable(dto.isAvailable());

        return entity;
    }

    public List<PeriodicalDTO> listToDTO(List<Periodical> periodicalList) {
        List<PeriodicalDTO> result = new ArrayList<>();

        for (Periodical periodical : periodicalList) {
            result.add(toDTO(periodical));
        }

        return result;
    }
}
