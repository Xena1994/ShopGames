package pl.gameshop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.gameshop.models.Product;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    @Query("SELECT p FROM Product p WHERE " +
            "(" +
            ":phrase is null OR :phrase = '' OR "+
            "upper(p.name) LIKE upper(:phrase) OR " +
            "upper(p.platform.name) LIKE upper(:phrase) OR " +
            "upper(p.category.name) LIKE upper(:phrase)" +
            ") " +
            "AND " +
            "(:min is null OR :min <= p.price) " +
            "AND (:max is null OR :max >= p.price)" +
            "OR (p.platform.id = :pId)" +
            "OR (p.category.id = :cId)")
    Page<Product> findAllProductsUsingFilter(@Param("phrase") String p, @Param("min") BigDecimal priceMin,
                                             @Param("max") BigDecimal priceMax, @Param("pId") Long pId,
                                             @Param("cId") Long cId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :id")
    Page<Product> findAllProductsByCategory(@Param("id") Long id, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.platform.id = :id")
    Page<Product> findAllProductsByPlatform(@Param("id") Long id, Pageable pageable);

}
