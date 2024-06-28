package uniqueproject.uz.go2uzproject.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uniqueproject.uz.go2uzproject.dto.request.FilterToursRequest;
import uniqueproject.uz.go2uzproject.dto.request.TourRequest;
import uniqueproject.uz.go2uzproject.dto.request.TourSpecification;
import uniqueproject.uz.go2uzproject.dto.request.TourUpdateRequest;
import uniqueproject.uz.go2uzproject.dto.response.ReviewResponse;
import uniqueproject.uz.go2uzproject.dto.response.TourResponse;
import uniqueproject.uz.go2uzproject.entity.Agency;
import uniqueproject.uz.go2uzproject.entity.Review;
import uniqueproject.uz.go2uzproject.entity.Tour;
import uniqueproject.uz.go2uzproject.repository.AgencyRepository;
import uniqueproject.uz.go2uzproject.repository.TourRepository;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;
    private final AgencyRepository agencyRepository;
    private final ModelMapper modelMapper;

    public TourResponse addTourToAgency(UUID agencyId, TourRequest tourRequest) {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new EntityNotFoundException("Agency not found with id: " + agencyId));

        Tour newTour = Tour.builder()
                .agency(agency)
                .category(tourRequest.getCategory())
                .cost(tourRequest.getCost())
                .status(tourRequest.getStatus())
                .startDate(tourRequest.getStartDate())
                .endDate(tourRequest.getEndDate())
                .availableSeats(tourRequest.getAvailableSeats())
                .description(tourRequest.getDescription())
                .location(tourRequest.getLocation())
                .services(tourRequest.getServices())
                .title(tourRequest.getTitle())
                .pictures(tourRequest.getPictures())
                .cancellationPolicy(tourRequest.getCancellationPolicy())
                .build();
        tourRepository.save(newTour);
        agency.getTours().add(newTour);
        agencyRepository.save(agency);

        TourResponse tourResponse = modelMapper.map(newTour, TourResponse.class);
        tourResponse.setAgencyName(agency.getName());

        return tourResponse;
    }

    public String updateTour(TourUpdateRequest tourUpdateRequest) {

        Tour tour = tourRepository.findById(tourUpdateRequest.getTourId())
                .orElseThrow(() -> new EntityNotFoundException("Tour not found with id: " + tourUpdateRequest.getTourId()));

        if (tourUpdateRequest.getAvailableSeats() != null) {
            tour.setAvailableSeats(tourUpdateRequest.getAvailableSeats());
        }
        if (tourUpdateRequest.getCategory() != null) {
            tourUpdateRequest.setCategory(tourUpdateRequest.getCategory());
        }
        if (tourUpdateRequest.getCost() != null) {
            tour.setCost(tourUpdateRequest.getCost());
        }
        if (tourUpdateRequest.getDescription() != null) {
            tour.setDescription(tourUpdateRequest.getDescription());
        }
        if (tourUpdateRequest.getEndDate() != null) {
            tour.setEndDate(tourUpdateRequest.getEndDate());
        }
        if (tourUpdateRequest.getLocation() != null) {
            tour.setLocation(tourUpdateRequest.getLocation());
        }
        if (tourUpdateRequest.getServices() != null) {
            tour.setServices(tourUpdateRequest.getServices());
        }
        if (tourUpdateRequest.getPictures() != null) {
            tour.setPictures(tourUpdateRequest.getPictures());
        }
        if (tourUpdateRequest.getStartDate() != null) {
            tour.setStartDate(tourUpdateRequest.getStartDate());
        }
        if (tourUpdateRequest.getStatus() != null) {
            tour.setStatus(tourUpdateRequest.getStatus());
        }
        if (tourUpdateRequest.getTitle() != null) {
            tour.setTitle(tourUpdateRequest.getTitle());
        }
        if (tourUpdateRequest.getCancellationPolicy() != null){
            tour.setCancellationPolicy(tourUpdateRequest.getCancellationPolicy());
        }

        tour.setUpdatedDate(LocalDateTime.now());
        tourRepository.save(tour);

        return "Tour updated successfully";
    }


    public List<TourResponse> filterTours(FilterToursRequest filterRequest) {
        Specification<Tour> spec = Specification.where(TourSpecification.hasStartDate(filterRequest.getStartDate()))
                .and(TourSpecification.hasEndDate(filterRequest.getEndDate()))
                .and(TourSpecification.hasPriceRange(filterRequest.getMinPrice(), filterRequest.getMaxPrice()))
                .and(TourSpecification.hasLocation(filterRequest.getLocation()))
                .and(TourSpecification.hasCategory(filterRequest.getCategory()));

        List<Tour> tours = tourRepository.findAll(spec);
        return tours.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private TourResponse convertToDto(Tour tour) {
        return TourResponse.builder()
                .id(tour.getId())
                .title(tour.getTitle())
                .description(tour.getDescription())
                .pictures(tour.getPictures())
                .cost(tour.getCost())
                .location(tour.getLocation())
                .category(tour.getCategory())
                .status(tour.getStatus())
                .agencyName(tour.getAgency().getName())
                .startDate(tour.getStartDate())
                .endDate(tour.getEndDate())
                .availableSeats(tour.getAvailableSeats())
                .services(tour.getServices())
                .cancellationPolicy(tour.getCancellationPolicy())
                .reviews(tour.getReviews().stream().map(this::convertReviewToDto).collect(Collectors.toList()))
                .build();
    }

    private ReviewResponse convertReviewToDto(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .authorName(review.getAuthor().getName())
                .content(review.getContent())
                .build();
    }

    public String delete(UUID tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new EntityNotFoundException("Tour not found with id: " + tourId));
        tourRepository.delete(tour);
        return "Tour deleted successfully";
    }

    public TourResponse getTour(UUID tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new EntityNotFoundException("Tour not found with id: " + tourId));
        TourResponse tourResponse = modelMapper.map(tour, TourResponse.class);
        tourResponse.setId(tour.getId());
        tourResponse.setAgencyName(tour.getAgency().getName());
        return tourResponse;
    }
}
