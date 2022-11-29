package pl.gameshop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.gameshop.models.Category;
import pl.gameshop.models.Product;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Product> findByName(String phrase, Pageable pageable);
}
