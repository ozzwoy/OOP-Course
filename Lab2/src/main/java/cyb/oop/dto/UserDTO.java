package cyb.oop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserDTO {
    @NonNull
    private long id;
    @NonNull
    private String username;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String role;
}
