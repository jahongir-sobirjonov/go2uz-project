package uniqueproject.uz.go2uzproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uniqueproject.uz.go2uzproject.dto.request.RatingRequest;
import uniqueproject.uz.go2uzproject.dto.response.RatingResponse;
import uniqueproject.uz.go2uzproject.service.RatingService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add-rating")
    public ResponseEntity<RatingResponse> leaveRating(@Valid @RequestBody RatingRequest ratingRequest, Principal principal) {
        RatingResponse ratingResponse = ratingService.addRating(ratingRequest, UUID.fromString(principal.getName()));
        return ResponseEntity.ok(ratingResponse);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/cancel-rating/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable UUID ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tour/{tourId}")
    public ResponseEntity<List<RatingResponse>> getRatingsByTour(@PathVariable UUID tourId) {
        List<RatingResponse> ratings = ratingService.getRatingsByTour(tourId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/tour/{tourId}/average")
    public ResponseEntity<Double> getAverageRatingByTour(@PathVariable UUID tourId) { // Tur bo'yicha o'rtacha reytingni olish
        double averageRating = ratingService.getAverageRatingByTour(tourId);
        return ResponseEntity.ok(averageRating);
    }

}
