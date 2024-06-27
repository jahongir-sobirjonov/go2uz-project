package uniqueproject.uz.go2uzproject.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private UUID userId;
    private UUID tourId;
    private String phoneNumber;
    private Integer NumberOfSeats;
//    private String paymentMethod;
    private String url; // Added field for URL if needed

}
