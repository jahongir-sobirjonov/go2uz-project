package uniqueproject.uz.go2uzproject.dto.response;

import lombok.*;
import uniqueproject.uz.go2uzproject.entity.enums.TourCategory;
import uniqueproject.uz.go2uzproject.entity.enums.TourStatus;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TourResponse {
    private UUID id;
    private String title;
    private String description;
    private List<String> pictures;
    private Double cost;
    private String location;
    private TourCategory category;
    private TourStatus status;
    private String agencyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer availableSeats;
    private List<String> services;
    private String cancellationPolicy;
    private List<ReviewResponse> reviews;
}
