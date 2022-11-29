package pl.gameshop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.gameshop.models.Category;
import pl.gameshop.models.Product;

public interface CategoryService {

    Page<Product> getAllProductsbyCategory(Long id, Pageable pageable);

    Page<Category> getAllCategories(Pageable pageable);

    Category getCategory(Long id);

    void deleteCategory(Long id);

    void saveCategory(Category category);
}
