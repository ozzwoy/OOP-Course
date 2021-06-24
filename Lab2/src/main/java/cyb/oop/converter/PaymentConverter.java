package cyb.oop.converter;

import cyb.oop.dto.PaymentDTO;
import cyb.oop.dto.PeriodicalDTO;
import cyb.oop.entity.Payment;
import cyb.oop.entity.Periodical;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentConverter {

    public PaymentDTO toDTO(Payment entity) {
        PaymentDTO dto = new PaymentDTO();

        dto.setId(entity.getId());
        dto.setSum(entity.getSum());

        return dto;
    }

    public Payment toEntity(PaymentDTO dto) {
        Payment entity = new Payment();

        entity.setId(dto.getId());
        entity.setSum(dto.getSum());

        return entity;
    }

    public List<PaymentDTO> listToDTO(List<Payment> paymentList) {
        List<PaymentDTO> result = new ArrayList<>();

        for (Payment payment : paymentList) {
            result.add(toDTO(payment));
        }

        return result;
    }
}
