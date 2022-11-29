package pl.gameshop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.gameshop.models.AvailabilityType;

public interface AvailabilityService {

    Page<AvailabilityType> getAllTypes(Pageable pageable);

    AvailabilityType getType(Long id);

    void deleteType(Long id);

    void saveType(AvailabilityType type);
}
