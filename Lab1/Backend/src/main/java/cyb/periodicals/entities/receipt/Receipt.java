package cyb.periodicals.entities.receipt;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class Receipt {
    @NonNull private Long id;
    @NonNull private Integer sum;
    @NonNull private LocalDateTime doneAt;
}
