package uniqueproject.uz.go2uzproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniqueproject.uz.go2uzproject.dto.request.ReviewRequest;
import uniqueproject.uz.go2uzproject.dto.response.ReviewResponse;
import uniqueproject.uz.go2uzproject.entity.Review;
import uniqueproject.uz.go2uzproject.entity.Tour;
import uniqueproject.uz.go2uzproject.entity.UserEntity;
import uniqueproject.uz.go2uzproject.exception.DataNotFoundException;
import uniqueproject.uz.go2uzproject.repository.ReviewRepository;
import uniqueproject.uz.go2uzproject.repository.TourRepository;
import uniqueproject.uz.go2uzproject.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;

    public ReviewResponse writeReview(ReviewRequest reviewRequest, UUID authorId) {
        Tour tour = tourRepository.findById(reviewRequest.getTourId())
                .orElseThrow(() -> new DataNotFoundException("Tour not found"));

        UserEntity author = userRepository.findById(authorId).orElseThrow(() -> new DataNotFoundException("Author not found"));

        Review review = Review.builder()
                .author(author)
                .content(reviewRequest.getContent())
                .tour(tour)
                .build();

        review = reviewRepository.save(review);
        return convertToDto(review);
    }



    public void deleteReview(UUID reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public List<ReviewResponse> getReviewsByTour(UUID tourId) {
        List<Review> reviews = reviewRepository.findByTourId(tourId);
        return reviews.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ReviewResponse convertToDto(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .authorName(review.getAuthor().getName())
                .content(review.getContent())
//                .replies(review.getReplies().stream().map(this::convertToDto).collect(Collectors.toList()))
                .build();
    }
}
