package cyb.oop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import cyb.oop.converter.UserConverter;
import cyb.oop.dto.UserDTO;
import cyb.oop.entity.User;
import cyb.oop.repository.UserRepository;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserService(UserRepository repo, UserConverter userConverter) {
        this.userRepository = repo;
        this.userConverter = userConverter;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get(0);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> answer = new ArrayList<>();
        for (User user : users) {
            answer.add(userConverter.toDTO(user));
        }
        return answer;
    }

    @SuppressWarnings("unchecked")
    public User getUser(String Authorization) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            if (authentication.getPrincipal() instanceof KeycloakPrincipal) {
                KeycloakPrincipal<KeycloakSecurityContext> kp =
                        (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
                AccessToken accessToken = kp.getKeycloakSecurityContext().getToken();
                String username = accessToken.getPreferredUsername();

                return findByUsername(username);
            }
        }

        return null;
    }
}
