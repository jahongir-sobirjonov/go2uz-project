package uniqueproject.uz.go2uzproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniqueproject.uz.go2uzproject.dto.request.RatingRequest;
import uniqueproject.uz.go2uzproject.dto.response.RatingResponse;
import uniqueproject.uz.go2uzproject.entity.Agency;
import uniqueproject.uz.go2uzproject.entity.Rating;
import uniqueproject.uz.go2uzproject.entity.Tour;
import uniqueproject.uz.go2uzproject.entity.UserEntity;
import uniqueproject.uz.go2uzproject.exception.DataNotFoundException;
import uniqueproject.uz.go2uzproject.repository.AgencyRepository;
import uniqueproject.uz.go2uzproject.repository.RatingRepository;
import uniqueproject.uz.go2uzproject.repository.TourRepository;
import uniqueproject.uz.go2uzproject.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final AgencyRepository agencyRepository;


    public RatingResponse addRating(RatingRequest ratingRequest, UUID authorId) {
        // Fetch the tour and author from the repositories
        Tour tour = tourRepository.findById(ratingRequest.getTourId())
                .orElseThrow(() -> new DataNotFoundException("Tour not found"));
        UserEntity author = userRepository.findById(authorId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Check if the user has already rated this tour
        if (ratingRepository.existsByAuthorIdAndTour(authorId, tour)) {
            throw new IllegalArgumentException("User has already rated this tour");
        }
        Agency agency = tour.getAgency();

        // Initialize tour rating if null
        if (tour.getRating() == null) {
            tour.setRating(0);
        }

        // Initialize agency rating if null
        if (tour.getAgency().getRating() == null) {
            tour.getAgency().setRating(0.0);
        }

        // Update the agency rating
        if (ratingRequest.getRating() != 0) {
            agency.setRating(agency.getRating() + (double) ratingRequest.getRating() / 10);
        } else {
            agency.setRating(agency.getRating() + ratingRequest.getRating());
        }
        agencyRepository.save(agency);

        // Update the tour rating
        tour.setRating(tour.getRating() + ratingRequest.getRating());
        tourRepository.save(tour);

        // Create a new Rating entity and save it
        Rating rating = Rating.builder()
                .author(author)
                .rating(ratingRequest.getRating())
                .tour(tour)
                .build();

        rating = ratingRepository.save(rating);

        // Return the RatingResponse DTO
        return convertToDto(rating);
    }


    public void deleteRating(UUID ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    public List<RatingResponse> getRatingsByTour(UUID tourId) {
        List<Rating> ratings = ratingRepository.findByTourId(tourId);
        return ratings.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public double getAverageRatingByTour(UUID tourId) {
        List<Rating> ratings = ratingRepository.findByTourId(tourId);
        return ratings.stream().mapToInt(Rating::getRating).average().orElse(0.0);
    }

    private RatingResponse convertToDto(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .authorName(rating.getAuthor().getName())
                .rating(rating.getRating())
                .build();
    }
}
