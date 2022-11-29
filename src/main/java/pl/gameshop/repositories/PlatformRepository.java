package pl.gameshop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.gameshop.models.Platform;
import pl.gameshop.models.Product;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

    Page<Product> findByNameContaining(String phrase, Pageable pageable);
}
