package pl.gameshop.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.gameshop.models.*;
import pl.gameshop.repositories.*;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class RepositoryMockupInitializer implements InitializingBean {


    private AvailabilityTypeRepository availabilityTypeRepository;

    private ProductRepository productRepository;

    private PlatformRepository platformRepository;

    private LanguageRepository languageRepository;

    private CategoryRepository categoryRepository;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public RepositoryMockupInitializer(AvailabilityTypeRepository availabilityTypeRepository, ProductRepository productRepository, PlatformRepository platformRepository, LanguageRepository languageRepository, CategoryRepository categoryRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.availabilityTypeRepository = availabilityTypeRepository;
        this.productRepository = productRepository;
        this.platformRepository = platformRepository;
        this.languageRepository = languageRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void afterPropertiesSet(){
        initAvailabilityTypeRepository();
        initCategoryRepository();
        initLanguageRepository();
        initPlatformRepository();
        initRoleRepository();
    }

    public void initAvailabilityTypeRepository() {
        if(availabilityTypeRepository.findAll().isEmpty()){
            AvailabilityType a1 = new AvailabilityType();
            AvailabilityType a2 = new AvailabilityType();
            AvailabilityType a3 = new AvailabilityType();

            a1.setName("Dostępne 24h");
            a1.setInStock(true);
            availabilityTypeRepository.save(a1);

            a2.setName("Preorder");
            a2.setInStock(true);
            availabilityTypeRepository.save(a2);

            a3.setName("Brak w magazynie");
            a3.setInStock(false);
            availabilityTypeRepository.save(a3);
        }

    }
    public void initCategoryRepository() {
        if(categoryRepository.findAll().isEmpty()){
            Category c1 = new Category();
            Category c2 = new Category();
            Category c3 = new Category();

            c1.setName("Wyścigowa");
            categoryRepository.save(c1);

            c2.setName("Sportowa");
            categoryRepository.save(c2);

            c3.setName("Muzyczna");
            categoryRepository.save(c3);
        }
    }
    public void initPlatformRepository() {
        if(platformRepository.findAll().isEmpty()){
            Platform p1 = new Platform();
            Platform p2 = new Platform();
            Platform p3 = new Platform();

            p1.setName("PC");
            platformRepository.save(p1);

            p2.setName("PC Digital");
            platformRepository.save(p2);

            p3.setName("PS4");
            platformRepository.save(p3);
        }
    }
    public void initLanguageRepository(){
        if(languageRepository.findAll().isEmpty()){
            Language a1 = new Language();
            Language a2 = new Language();
            Language a3 = new Language();

            a1.setName("polski");
            languageRepository.save(a1);

            a2.setName("angielski");
            languageRepository.save(a2);

            a3.setName("japonski");
            languageRepository.save(a3);
        }
    }

    public void initRoleRepository(){
        if(roleRepository.findAll().isEmpty() == true){
            try {
                Role roleUser = roleRepository.save(new Role(Role.Types.ROLE_USER));
                Role roleAdmin = roleRepository.save(new Role(Role.Types.ROLE_ADMIN));

                User user = new User("user", true);
                user.setEmail("example@gmail.com");
                user.setRoles(new HashSet<>(Arrays.asList(roleUser)));
                user.setPassword(passwordEncoder.encode("user"));

                User admin = new User("admin", true);
                user.setEmail("example2@gmail.com");
                admin.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));
                admin.setPassword(passwordEncoder.encode("admin"));

                User test = new User("test", true);
                user.setEmail("example3@gmail.com");
                test.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleUser)));
                test.setPassword(passwordEncoder.encode("test"));

                userRepository.save(user);
                userRepository.save(admin);
                userRepository.save(test);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
