package cyb.periodicals.entities.receipt;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Setter
public class ReceiptBuilder {
    private Long id;
    private Integer sum;
    private LocalDateTime doneAt;

    public Receipt build() {
        return new Receipt(id, sum, doneAt);
    }
}
