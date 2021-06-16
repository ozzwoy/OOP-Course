package cyb.periodicals.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class User {
    @NonNull private Long id;
    @NonNull private String username;
    @NonNull private String password;
    @NonNull private Role role;
             private String name;
             private String address;
}
