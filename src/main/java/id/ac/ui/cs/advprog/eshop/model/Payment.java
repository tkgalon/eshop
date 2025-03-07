package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Getter
public class Payment {
    String id;
    Order order;
    String method;
    Map<String, String> paymentData;
    String status;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.setStatusBasePaymentData(paymentData, method);
        this.paymentData = paymentData;

    }

    public Payment(String id, Order order, String status, String method, Map<String, String> paymentData) {
        this(id, order, method, paymentData);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        }
        else {
            throw new IllegalArgumentException();
        }
    }


    public void setStatusBasePaymentData(Map<String, String> paymentData, String nameMethod) {
        if ("BANK".equals(nameMethod)) {
            String bankName = paymentData.get("bankName");
            String referenceCode = paymentData.get("referenceCode");

            if (Objects.isNull(bankName) || Objects.isNull(referenceCode) || bankName.isEmpty() || referenceCode.isEmpty()) {
                this.status = PaymentStatus.REJECTED.getValue();
            } else {
                this.status = PaymentStatus.PENDING.getValue();
            }
        }

        else if ("VOUCHER".equals(nameMethod)) {
            String voucherCode = paymentData.get("voucherCode");

            if (Objects.isNull(voucherCode) || voucherCode.isEmpty()) {
                this.status = PaymentStatus.REJECTED.getValue();
                return;
            }

            if (voucherCode.length() != 16) {
                this.status = PaymentStatus.REJECTED.getValue();
                return;
            }


            if (!voucherCode.startsWith("ESHOP")) {
                this.status = PaymentStatus.REJECTED.getValue();
                return;
            }

            long countDigits = voucherCode.chars().filter(Character::isDigit).count();
            if (countDigits != 8) {
                this.status = PaymentStatus.REJECTED.getValue();
                return;
            }

            this.status = PaymentStatus.SUCCESS.getValue();
        }
    }



}
