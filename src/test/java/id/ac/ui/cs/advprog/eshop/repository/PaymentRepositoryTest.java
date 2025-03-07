package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        payments = new ArrayList<>();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(1);
        products.add(product1);

        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");

        Order order2 = new Order("e334ef40-9eff-4da8-9487-8ee697ecbf1e",
                products, 1708570000L, "Bambang Sudrajat");

        Map<String, String> paymentDataBank = new HashMap<>();
        paymentDataBank.put("bankName", "Mandiri");
        paymentDataBank.put("referenceCode", "12345");

        Map<String, String> productDataVoucher = new HashMap<>();
        productDataVoucher.put("voucherCode", "ESHOP1234ABC5678");

        Payment paymentA = new Payment("1d-untuk-p4ym4nt", order1,"BANK", paymentDataBank);
        Payment paymentB = new Payment("2d-untuk-p4ym4nt", order2, "VOUCHER", productDataVoucher);

        payments.add(paymentA);
        payments.add(paymentB);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(1);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getOrder(), findResult.getOrder());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        Map<String, String> newPaymentData = new HashMap<>();
        newPaymentData.put("bankName", "BANKJAGO");
        newPaymentData.put("referenceCode", "54321");

        Payment updatedPayment = new Payment("1d-untuk-p4ym4nt", payment.getOrder(),"BANK", newPaymentData);

        Payment result = paymentRepository.save(updatedPayment);
        Payment findResult = paymentRepository.findById(payments.get(0).getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(result.getId(), findResult.getId());
        assertEquals(result.getOrder(), findResult.getOrder());
        assertEquals(result.getStatus(), findResult.getStatus());
        assertEquals(result.getMethod(), findResult.getMethod());
        assertEquals(result.getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payments.get(1).getId(), findResult.getId());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertEquals(payments.get(1).getOrder(), findResult.getOrder());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
        assertEquals(payments.get(1).getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById("zczc");
        assertNull(findResult);
    }

    @Test
    void testFindAllPayments() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        List<Payment> allPayments = paymentRepository.findAll();
        assertEquals(2, allPayments.size());
    }

}
