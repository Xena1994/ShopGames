package pl.gameshop.validators;

import org.springframework.beans.factory.annotation.Autowired;
import pl.gameshop.validators.annotations.UniqueEmail;
import pl.gameshop.validators.annotations.UniqueUsername;
import pl.gameshop.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserService userService;

    public void initialize(UniqueUsername constraint) {
    }

    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userService == null || (email != null && userService.isUniqueEmail(email));
    }

}