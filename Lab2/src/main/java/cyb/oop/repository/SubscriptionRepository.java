package cyb.oop.repository;

import java.util.List;

import cyb.oop.entity.Periodical;
import cyb.oop.entity.Subscription;
import cyb.oop.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUser(User user);

    List<Subscription> findByPeriodical(Periodical periodical);
}
