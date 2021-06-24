package cyb.oop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SubscriptionDTO {
    @NonNull
    private long id;
    @NonNull
    private UserDTO user;
    @NonNull
    private PeriodicalDTO periodical;
    @NonNull
    private PaymentDTO payment;
    @NonNull
    private LocalDate expirationDate;
}
