package uniqueproject.uz.go2uzproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import uniqueproject.uz.go2uzproject.dto.request.AgencyRequest;
import uniqueproject.uz.go2uzproject.dto.response.AgencyResponse;
import uniqueproject.uz.go2uzproject.entity.Agency;
import uniqueproject.uz.go2uzproject.entity.enums.ServiceType;
import uniqueproject.uz.go2uzproject.service.AgencyService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("agencies")
public class AgencyController { // admin controller
    private final AgencyService agencyService;

   @GetMapping("/get{id}")
    public Optional<Agency> getAgencies(@PathVariable UUID id) {
       return agencyService.getById(id);
   }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/user-agencies")
    public ResponseEntity<List<AgencyResponse>> getUserAgencies(@RequestParam UUID userId) {
        List<AgencyResponse> agencies = agencyService.getAgenciesByUserId(userId);
        return ResponseEntity.ok(agencies);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-agency")
    public ResponseEntity<AgencyResponse> createAgency(
            @Valid @RequestBody AgencyRequest agencyRequest,
            @RequestParam List<ServiceType> serviceTypes,
            Principal principal) {
        return ResponseEntity.status(200)
                .body(agencyService.createAgency(agencyRequest, serviceTypes, UUID.fromString(principal.getName())));
    }

}
