package cyb.periodicals.entities.subscription;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Accessors(chain = true)
@Setter
public class SubscriptionBuilder {
    private Long id;
    private Long userId;
    private Long periodicalId;
    private Boolean confirmed;
    private LocalDate startDate;
    private LocalDate endDate;
    private Period period;
    private Long receiptId;

    public Subscription build() {
        return new Subscription(id, userId, periodicalId, confirmed, startDate, endDate, period, receiptId);
    }
}
