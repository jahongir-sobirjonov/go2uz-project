package uniqueproject.uz.go2uzproject.dto.request;

import lombok.*;
import uniqueproject.uz.go2uzproject.entity.enums.TourCategory;
import uniqueproject.uz.go2uzproject.entity.enums.TourStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TourUpdateRequest {
    private UUID tourId;
    private String title;
    private String description; //  joy haqida description
    private List<String> pictures; // rasmlar pathi
    private Double cost; // narxi
    private String location;
    private List<String> services;
    private TourCategory category;
    private TourStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer availableSeats;
    private String cancellationPolicy;
}
