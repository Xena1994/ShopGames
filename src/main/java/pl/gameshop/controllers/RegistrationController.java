package pl.gameshop.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import pl.gameshop.models.User;
import pl.gameshop.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userCommand", new User());
        return "registrationForm";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("userCommand") User userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registrationForm";
        }
        userForm.setEnabled(true);
        userService.save(userForm);
        return "registrationSuccess";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("enabled", "roles");
    }
}
