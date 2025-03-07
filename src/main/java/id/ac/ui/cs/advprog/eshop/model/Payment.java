package id.ac.ui.cs.advprog.eshop.model;

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

        String[] statusList = {"PENDING", "REJECTED", "SUCCESS"};
        if (Arrays.stream(statusList).noneMatch(item -> (item.equals(status)))) {
            throw new IllegalArgumentException();
        }
        else {
            this.status = status;
        }
    }

    public void setStatus(String status) {
        String[] statusList = {"PENDING", "REJECTED", "SUCCESS"};
        if (Arrays.stream(statusList).noneMatch(item -> (item.equals(status)))) {
            throw new IllegalArgumentException();
        }
        else {
            this.status = status;
        }
    }


    public void setStatusBasePaymentData(Map<String, String> paymentData, String nameMethod) {
        if ("BANK".equals(nameMethod)) {
            String bankName = paymentData.get("bankName");
            String referenceCode = paymentData.get("referenceCode");

            if (Objects.isNull(bankName) || Objects.isNull(referenceCode) || bankName.isEmpty() || referenceCode.isEmpty()) {
                this.status = "REJECTED";
            } else {
                this.status = "PENDING";
            }
        }

        else if ("VOUCHER".equals(nameMethod)) {
            String voucherCode = paymentData.get("voucherCode");

            if (Objects.isNull(voucherCode) || voucherCode.isEmpty()) {
                this.status = "REJECTED";
                return;
            }

            if (voucherCode.length() != 16) {
                this.status = "REJECTED";
                return;
            }

            if (!voucherCode.startsWith("ESHOP")) {
                this.status = "REJECTED";
                return;
            }

            long countDigits = voucherCode.chars().filter(Character::isDigit).count();
            if (countDigits != 8) {
                this.status = "REJECTED";
                return;
            }

            this.status = "SUCCESS";
        }
    }



}
