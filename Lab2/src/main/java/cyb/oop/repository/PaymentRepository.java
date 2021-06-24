package cyb.oop.repository;

import java.util.List;
import java.util.Optional;

import cyb.oop.entity.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> { }
