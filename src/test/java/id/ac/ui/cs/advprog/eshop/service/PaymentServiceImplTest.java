package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    private Order order;
    private Map<String, String> paymentDataBank;
    private Map<String, String> paymentDataVoucher;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");

        paymentDataBank = new HashMap<>();
        paymentDataBank.put("bankName", "Mandiri");
        paymentDataBank.put("referenceCode", "12345");

        paymentDataVoucher = new HashMap<>();
        paymentDataVoucher.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testAddPaymentBankTransferValid() {
        Payment payment = new Payment(order.getId(), order, "BANK", paymentDataBank);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.addPayment(order, "BANK", paymentDataBank);

        assertNotNull(result);
        assertEquals(PaymentStatus.PENDING.getValue(), result.getStatus());
        assertEquals("BANK", result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentBankTransferInvalid() {
        paymentDataBank.put("referenceCode", "");
        Payment result = paymentService.addPayment(order, "BANK", paymentDataBank);

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentVoucherValid() {
        Payment payment = new Payment(order.getId(), order, "VOUCHER", paymentDataVoucher);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentDataVoucher);

        assertNotNull(result);
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals("VOUCHER", result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentVoucherInvalid() {
        paymentDataVoucher.put("voucherCode", "INVALIDCODE");
        Payment result = paymentService.addPayment(order, "VOUCHER", paymentDataVoucher);

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = new Payment(order.getId(), order, PaymentStatus.PENDING.getValue(), "BANK", paymentDataBank);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals("SUCCESS", order.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = new Payment(order.getId(), order, PaymentStatus.PENDING.getValue(), "BANK", paymentDataBank);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals("FAILED", order.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testSetStatusInvalid() {
        Payment payment = new Payment(order.getId(), order, PaymentStatus.PENDING.getValue(), "BANK", paymentDataBank);
        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(payment, "INVALID"));
    }

    @Test
    void testGetPaymentValid() {
        Payment payment = new Payment(order.getId(), order, PaymentStatus.PENDING.getValue(), "BANK", paymentDataBank);
        when(paymentRepository.findById(order.getId())).thenReturn(payment);

        Payment result = paymentService.getPayment(order.getId());

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        verify(paymentRepository, times(1)).findById(order.getId());
    }

    @Test
    void testGetPaymentInvalid() {
        when(paymentRepository.findById("invalid")).thenReturn(null);

        Payment result = paymentService.getPayment("invalid");

        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(new Payment(order.getId(), order, PaymentStatus.PENDING.getValue(), "BANK", paymentDataBank)));

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(1, result.size());
        verify(paymentRepository, times(1)).findAll();
    }
}
