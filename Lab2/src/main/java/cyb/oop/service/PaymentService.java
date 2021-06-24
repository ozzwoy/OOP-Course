package cyb.oop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cyb.oop.converter.PaymentConverter;
import cyb.oop.dto.PaymentDTO;
import cyb.oop.entity.Periodical;
import cyb.oop.entity.Payment;
import cyb.oop.entity.User;
import cyb.oop.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment pay(int sum) {
        Payment payment = new Payment(sum);
        paymentRepository.save(payment);

        return payment;
    }
}
