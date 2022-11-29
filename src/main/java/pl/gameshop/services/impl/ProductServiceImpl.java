package pl.gameshop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gameshop.controllers.commands.ProductFilter;
import pl.gameshop.exceptions.ItemNotFoundException;
import pl.gameshop.models.*;
import pl.gameshop.repositories.*;
import pl.gameshop.services.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private AvailabilityTypeRepository availabilityTypeRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PlatformRepository platformRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<AvailabilityType> getAllAvailabilityTypes() {
        return availabilityTypeRepository.findAll();
    }

    @Override
    public List<Platform> getAllPlatforms() {
        return platformRepository.findAll();
    }

    @Override
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Product> getAllProducts(ProductFilter search, Pageable pageable) {
        Page page;
        if(search.isEmpty()){
            page = productRepository.findAll(pageable);
        }else{
            page = productRepository.findAllProductsUsingFilter(search.getPhraseLIKE(), search.getMinPrice(),
                    search.getMaxPrice(), search.getPlatform(), search.getCategory(), pageable);
        }
        return page;
    }

    @Transactional
    @Override
    public Product getProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.orElseThrow(()->new ItemNotFoundException(id));
        product.getLanguages().size();
        return product;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
