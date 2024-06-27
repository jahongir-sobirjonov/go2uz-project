package uniqueproject.uz.go2uzproject.forpayment.payme;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
class PaymentRequest {
    private String paymentType;
    private Double amount;
    private String returnUrl;
}
