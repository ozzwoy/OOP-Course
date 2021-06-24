package cyb.oop.repository;

import java.util.List;
import java.util.Optional;

import cyb.oop.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUsername(String login);
}
