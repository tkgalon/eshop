package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    private Map<String, String> paymentData;

    private List<Product> products;
    private Order order;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        this.products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);

        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);

        this.products.add(product1);
        this.products.add(product2);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");

    }

    void initPaymentMethod(String method) {
        if (method.equals("BANK")) {
            paymentData.put("bankName", "Mandiri");
            paymentData.put("referenceCode", "12345");
        }
        else {
            paymentData.put("voucherCode", "ESHOP1234ABC5678");
        }
    }

    @Test
    void testCreatePaymentDefault() {
        initPaymentMethod("BANK");
        Payment payment = new Payment("1d-untuk-p4ym4nt", this.order, "BANK", this.paymentData);

        // Check data inserted
        assertSame(this.order, payment.getOrder());
        assertEquals("BANK", payment.getMethod());
        assertSame(this.paymentData, payment.getPaymentData());

        assertEquals("PENDING", payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testCreatePaymentSuccessStatus() {
        initPaymentMethod("BANK");
        Payment payment = new Payment("1d-untuk-p4ym4nt", this.order, "SUCCESS", "BANK", this.paymentData);

        assertEquals("SUCCESS", payment.getStatus());
        paymentData.clear();

    }

    @Test
    void testCreatePaymentRejectedStatus() {
        initPaymentMethod("BANK");
        Payment payment = new Payment("1d-untuk-p4ym4nt", this.order, "REJECTED", "BANK", this.paymentData);

        assertEquals("REJECTED", payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        initPaymentMethod("BANK");
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("1d-untuk-p4ym4nt", this.order, "MEOW", "BANK", this.paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testSetStatusToSuccess() {
        initPaymentMethod("BANK");
        Payment payment = new Payment("1d-untuk-p4ym4nt", this.order, "BANK", this.paymentData);

        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testSetStatusToRejected() {
        initPaymentMethod("BANK");
        Payment payment = new Payment("1d-untuk-p4ym4nt", this.order, "BANK", this.paymentData);

        payment.setStatus("REJECTED");

        assertEquals("REJECTED", payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testSetStatusToInvalidStatus() {
        initPaymentMethod("BANK");
        Payment payment = new Payment("1d-untuk-p4ym4nt", this.order, "BANK", this.paymentData);

        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
        paymentData.clear();
    }


    // Test method payment
    @Test
    void testPaymentMethodBank() {
        initPaymentMethod("BANK");
        Payment payment = new Payment("1d-untuk-p4ym4nt", this.order, "BANK", this.paymentData);
        assertEquals("BANK", payment.getMethod());
        paymentData.clear();

    }

    @Test
    void testPaymentMethodVoucher() {
        initPaymentMethod("VOUCHER");
        Payment payment = new Payment("1d-untuk-p4ym4nt", this.order, "VOUCHER", this.paymentData);
        assertEquals("VOUCHER", payment.getMethod());
        paymentData.clear();

    }

    // Check status for valid and invalid init

    @Test
    void testPaymentBankValid() {
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", "12345");
        Payment payment = new Payment("1d-untuk-p4ym4nt", this.order, "BANK", this.paymentData);

        assertEquals("PENDING", payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testPaymentBankInvalid() {
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", null);
        Payment payment = new Payment("1d-untuk-p4ym4nt", this.order, "BANK", this.paymentData);

        assertEquals("REJECTED", payment.getStatus());
        paymentData.clear();
    }


    @Test
    void testPaymentVoucherValid(){
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("1d-untuk-p4ym4nt", this.order, "VOUCHER", this.paymentData);
        assertEquals("SUCCESS", payment.getStatus());
        paymentData.clear();

    }

    @Test
    void testPaymentVoucherInvalid(){
        this.paymentData.put("voucherCode", "Etsopp1234ABC5678");
        Payment payment = new Payment("1d-untuk-p4ym4nt", this.order, "VOUCHER", this.paymentData);
        assertEquals("REJECTED", payment.getStatus());
        paymentData.clear();
    }

}
