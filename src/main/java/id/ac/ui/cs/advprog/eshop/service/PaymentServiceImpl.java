package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(UUID.randomUUID().toString(), order, method, paymentData);
        if (paymentRepository.findById(payment.getId()) == null){
            paymentRepository.save(payment);
            return payment;
        }
        return null;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        payment.setStatus(status);
        paymentRepository.save(payment);

        if (PaymentStatus.SUCCESS.getValue().equals(status)) {
            payment.getOrder().setStatus("SUCCESS");
        } else if (PaymentStatus.REJECTED.getValue().equals(status)) {
            payment.getOrder().setStatus("FAILED");
        }

        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
