package cyb.oop.converter;

import cyb.oop.dto.SubscriptionDTO;
import cyb.oop.entity.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubscriptionConverter {
    private final UserConverter userConverter;
    private final PeriodicalConverter periodicalConverter;
    private final PaymentConverter paymentConverter;

    @Autowired
    public SubscriptionConverter(UserConverter userConverter, PeriodicalConverter periodicalConverter,
                                 PaymentConverter paymentConverter) {
        this.userConverter = userConverter;
        this.periodicalConverter = periodicalConverter;
        this.paymentConverter = paymentConverter;
    }

    public Subscription toEntity(SubscriptionDTO dto) {
        Subscription entity = new Subscription();

        entity.setId(dto.getId());
        entity.setUser(userConverter.toEntity(dto.getUser()));
        entity.setPeriodical(periodicalConverter.toEntity(dto.getPeriodical()));
        entity.setPayment(paymentConverter.toEntity(dto.getPayment()));
        entity.setExpirationDate(dto.getExpirationDate());

        return entity;
    }

    public SubscriptionDTO toDTO(Subscription entity) {
        SubscriptionDTO dto = new SubscriptionDTO();

        dto.setId(entity.getId());
        dto.setUser(userConverter.toDTO(entity.getUser()));
        dto.setPeriodical(periodicalConverter.toDTO(entity.getPeriodical()));
        dto.setPayment(paymentConverter.toDTO(entity.getPayment()));
        dto.setExpirationDate(entity.getExpirationDate());

        return dto;
    }

    public List<SubscriptionDTO> listToDTO(List<Subscription> subscriptionList) {
        List<SubscriptionDTO> result = new ArrayList<>();

        for (Subscription subscription : subscriptionList) {
            result.add(toDTO(subscription));
        }

        return result;
    }
}
