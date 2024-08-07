package uniqueproject.uz.go2uzproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniqueproject.uz.go2uzproject.dto.request.FilterToursRequest;
import uniqueproject.uz.go2uzproject.dto.request.TourRequest;
import uniqueproject.uz.go2uzproject.dto.request.TourUpdateRequest;
import uniqueproject.uz.go2uzproject.dto.response.TourResponse;
import uniqueproject.uz.go2uzproject.service.TourService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("tours")
public class TourController {
    private final TourService tourService;
    @PreAuthorize("hasRole('AGENCY')")
    @PostMapping("/add-tour")
    public ResponseEntity<TourResponse> addTour(@RequestBody TourRequest tourRequest) {
        TourResponse tourResponse = tourService.addTourToAgency(tourRequest.getAgencyId(), tourRequest);
        return ResponseEntity.status(200).body(tourResponse);
    }

    @PreAuthorize("hasRole('AGENCY')")
    @PatchMapping("/update-tour")
    public ResponseEntity<String> updateTour(@RequestBody TourUpdateRequest tourUpdateRequest) {
        return ResponseEntity.status(200).body(tourService.updateTour(tourUpdateRequest));

    }


    @PostMapping("/filter")
    public ResponseEntity<List<TourResponse>> filterTours(@RequestBody FilterToursRequest filterRequest) {
        List<TourResponse> tours = tourService.filterTours(filterRequest);
        return ResponseEntity.ok(tours);
    }
    @PreAuthorize("hasRole('AGENCY')")
    @DeleteMapping("/delete-tour{tourId}")
    public ResponseEntity<String> deleteTour(@PathVariable UUID tourId) {
        return ResponseEntity.status(200).body(tourService.delete(tourId));

    }

    @GetMapping("/get-tour{tourId}")
    public ResponseEntity<TourResponse> getTour(@PathVariable UUID tourId) {
        return ResponseEntity.ok(tourService.getTour(tourId));
    }

    @GetMapping("/get-all-tours")
    public ResponseEntity<List<TourResponse>> getAllTours() {
        return ResponseEntity.ok(tourService.getAll());
    }

    @GetMapping("/get-tours-by-agencyId{agencyId}")
    public ResponseEntity<List<TourResponse>> getToursByAgencyId(@PathVariable UUID agencyId) {
        return ResponseEntity.ok(tourService.getToursByAgencyId(agencyId));
    }

}
