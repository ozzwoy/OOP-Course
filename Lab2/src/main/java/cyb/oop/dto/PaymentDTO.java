package cyb.oop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PaymentDTO {
    @NonNull
    private long id;
    @NonNull
    private int sum;
}
