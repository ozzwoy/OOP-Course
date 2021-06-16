package cyb.periodicals.entities.user;

import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
public class UserBuilder {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private String name;
    private String address;

    public User build() {
        return new User(id, username, password, role, name, address);
    }
}
