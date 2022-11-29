package pl.gameshop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.gameshop.models.Platform;
import pl.gameshop.models.Product;

public interface PlatformService {

    Page<Product> getAllProductsbyPlatform(Long id, Pageable pageable);

    Page<Platform> getAllPlatforms(Pageable pageable);

    Platform getPlatform(Long id);

    void deletePlatform(Long id);

    void savePlatform(Platform platform);
}
