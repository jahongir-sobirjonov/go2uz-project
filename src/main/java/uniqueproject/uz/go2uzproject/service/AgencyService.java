package uniqueproject.uz.go2uzproject.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import uniqueproject.uz.go2uzproject.dto.request.AgencyRequest;
import uniqueproject.uz.go2uzproject.dto.response.AgencyResponse;
import uniqueproject.uz.go2uzproject.dto.response.TourResponse;
import uniqueproject.uz.go2uzproject.entity.Agency;
import uniqueproject.uz.go2uzproject.entity.UserEntity;
import uniqueproject.uz.go2uzproject.exception.DataAlreadyExistsException;
import uniqueproject.uz.go2uzproject.exception.DataNotFoundException;
import uniqueproject.uz.go2uzproject.repository.AgencyRepository;
import uniqueproject.uz.go2uzproject.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgencyService {
    private final AgencyRepository agencyRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TourService tourService;

    public Optional<Agency> getById(UUID id) {
        return agencyRepository.findById(id);
    }

    public List<AgencyResponse> getAgenciesByUserId(UUID adminId) {
//        List<Agency> agencies = agencyRepository.findByOwnerId(userId);
//        return agencies.stream().map(agency -> new AgencyResponse(
//                agency.getName(),
//                agency.getTours(),
//                agency.getServiceTypes(),
//                agency.getCountOfOrders(),
//                agency.getRating()
//        )).collect(Collectors.toList());
        List<Agency> byOwnerId = agencyRepository.findByOwnerId(adminId);
        return modelMapper.map(byOwnerId, new TypeToken<List<AgencyResponse>>() {
        }.getType());
    }

    public AgencyResponse createAgency(AgencyRequest agencyRequest,  UUID ownerId) {
        if (agencyRepository.existsByName(agencyRequest.getName())) {
            throw new DataAlreadyExistsException("Agency already exists with this name : " + agencyRequest.getName());
        }
        UserEntity owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        Agency agency = modelMapper.map(agencyRequest, Agency.class);
        agency.setRating(0.0);
        agency.setOwner(owner);
        agencyRepository.save(agency);

        return AgencyResponse.builder()
                .id(agency.getId())
                .name(agency.getName())
                .rating(agency.getRating())
                .build();

    }

    public List<AgencyResponse> getAll() {
        List<Agency> agencies = agencyRepository.findAll();
        return agencies.stream()
                .map(this::convertToAgencyResponse)
                .collect(Collectors.toList());
    }

    private AgencyResponse convertToAgencyResponse(Agency agency) {
        return AgencyResponse.builder()
                .id(agency.getId())
                .name(agency.getName())
                .rating(agency.getRating())
                .tours(tourService.getToursByAgencyId(agency.getId()))
                .build();
    }


}
