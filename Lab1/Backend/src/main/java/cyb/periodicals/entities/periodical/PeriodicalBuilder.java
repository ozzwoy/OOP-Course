package cyb.periodicals.entities.periodical;

import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
public class PeriodicalBuilder {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Integer priceForMonth;
    private Integer priceForYear;
    private Boolean available;

    public Periodical build() {
        return new Periodical(id, name, description, image, priceForMonth, priceForYear, available);
    }
}
