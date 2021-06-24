package cyb.oop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "periodicals", schema = "public")
@NoArgsConstructor
@RequiredArgsConstructor
public class Periodical {
    @Id
    @GeneratedValue(generator = "periodicals_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "periodicals_id_seq", sequenceName = "periodicals_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    @NonNull
    private long id;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "price")
    @NonNull
    private int price;

    @Column(name = "period")
    @NonNull
    private int period;

    @Column(name = "available")
    @NonNull
    private boolean available;
}
