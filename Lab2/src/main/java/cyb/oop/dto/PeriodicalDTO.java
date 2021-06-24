package cyb.oop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PeriodicalDTO {
    @NonNull
    private long id;
    @NonNull
    private String name;
    @NonNull
    private int price;
    @NonNull
    private int period;
    @NonNull
    private boolean available;
}
