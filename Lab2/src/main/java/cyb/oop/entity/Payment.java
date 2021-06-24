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
@Table(name = "payments", schema = "public")
@NoArgsConstructor
@RequiredArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(generator = "payments_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "payments_id_seq", sequenceName = "payments_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    @NonNull
    private long id;
    
    @Column(name = "sum")
    private int sum;
}
