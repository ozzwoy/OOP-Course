package cyb.oop.entity;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "subscription", schema = "public")
@NoArgsConstructor
@RequiredArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(generator = "subscriptions_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "subscriptions_id_seq", sequenceName = "subscriptions_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    @NonNull
    private long id;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "users", referencedColumnName="id")
    @NonNull
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "periodicals", referencedColumnName="id")
    @NonNull
    private Periodical periodical;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payments", referencedColumnName="id")
    @NonNull
    private Payment payment;

    @Column(name = "expiration_date", updatable = false)
    @NonNull
    private LocalDate expirationDate;
}
