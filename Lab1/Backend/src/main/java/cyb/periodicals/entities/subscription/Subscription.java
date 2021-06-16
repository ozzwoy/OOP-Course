package cyb.periodicals.entities.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class Subscription {
    @NonNull private Long id;
    @NonNull private Long userId;
    @NonNull private Long periodicalId;
    @NonNull private Boolean confirmed;
             private LocalDate startDate;
             private LocalDate endDate;
             private Period period;
             private Long receiptId;
}
