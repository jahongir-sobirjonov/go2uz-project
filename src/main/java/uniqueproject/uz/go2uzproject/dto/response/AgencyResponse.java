package uniqueproject.uz.go2uzproject.dto.response;

import lombok.*;
import uniqueproject.uz.go2uzproject.entity.enums.ServiceType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgencyResponse {
    private UUID id;
    private String name;
    private List<TourResponse> tours;
    private List<ServiceType> serviceTypes;
    private Integer countOfOrders;
    private Integer rating;
}


