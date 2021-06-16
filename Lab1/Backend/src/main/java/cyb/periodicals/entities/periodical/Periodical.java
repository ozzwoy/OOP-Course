package cyb.periodicals.entities.periodical;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class Periodical {
    @NonNull private Long id;
    @NonNull private String name;
    @NonNull private String description;
             private String image;
    @NonNull private Integer priceForMonth;
    @NonNull private Integer priceForYear;
    @NonNull private Boolean available;
}
