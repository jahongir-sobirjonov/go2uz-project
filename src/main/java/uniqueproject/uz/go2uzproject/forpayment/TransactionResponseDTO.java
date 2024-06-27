package uniqueproject.uz.go2uzproject.forpayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {
   private UUID transactionId;
    private UUID orderId;
    private UUID userId;
    private Double amount;
    private String url;
}
