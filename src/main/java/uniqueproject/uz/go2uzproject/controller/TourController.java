package uniqueproject.uz.go2uzproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-tour")
    public ResponseEntity<TourResponse> addTour(@RequestBody TourRequest tourRequest) {
        TourResponse tourResponse = tourService.addTourToAgency(tourRequest.getAgencyId(), tourRequest);
        return ResponseEntity.status(200).body(tourResponse);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update-tour")
    public ResponseEntity<String> updateTour(@RequestBody TourUpdateRequest tourUpdateRequest) {
        return ResponseEntity.status(200).body(tourService.updateTour(tourUpdateRequest));

    }

//    @GetMapping("/get-by-date")
//    public ResponseEntity<TourResponse> getTourByDate(@RequestParam String date) {}
    @PostMapping("/filter")
    public ResponseEntity<List<TourResponse>> filterTours(@RequestBody FilterToursRequest filterRequest) {
        List<TourResponse> tours = tourService.filterTours(filterRequest);
        return ResponseEntity.ok(tours);
    }

    @DeleteMapping("/delete-tour{tourId}")
    public ResponseEntity<String> deleteTour(@PathVariable UUID tourId) {
        return ResponseEntity.status(200).body(tourService.delete(tourId));

    }

    @GetMapping("/get-tour{tourId}")
    public ResponseEntity<TourResponse> getTour(@PathVariable UUID tourId) {
        return ResponseEntity.ok(tourService.getTour(tourId));
    }

}
