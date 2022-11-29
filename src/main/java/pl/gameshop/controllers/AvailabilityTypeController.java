package pl.gameshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.gameshop.services.AvailabilityService;
import pl.gameshop.models.AvailabilityType;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.Optional;

@Controller
@SessionAttributes("searchCommand")
public class AvailabilityTypeController {

    @Autowired
    private AvailabilityService availabilityService;

    @RequestMapping(value="/availabilityForm", method= RequestMethod.GET)
    public String showForm(Model model, Optional<Long> id){
        model.addAttribute("availability",
                id.isPresent()?
                        availabilityService.getType(id.get()):
                        new AvailabilityType());
        return "availabilityForm";
    }

    @RequestMapping(value="/availabilityForm", method= RequestMethod.POST)
    public String processForm(@Valid @ModelAttribute("availability") AvailabilityType type, BindingResult errors) {

        if(errors.hasErrors()){
            return "availabilityForm";
        }

        availabilityService.saveType(type);

        return "redirect:availability";//po udanym dodaniu/edycji przekierowujemy na listę
    }

    @RequestMapping(value="/availability", method = {RequestMethod.GET})
    public String showTypes(Model model, Pageable pageable){
        model.addAttribute("availabilityListPage", availabilityService.getAllTypes(pageable));
        return "availabilityList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/availability", params={"did"})
    public String deleteType(long did, HttpServletRequest request){
        availabilityService.deleteType(did);
        String queryString = prepareQueryString(request.getQueryString());
        return String.format("redirect:availability%s", queryString);//robimy przekierowanie, ale zachowując parametry pageingu
    }

    private String prepareQueryString(String queryString) {//np., did=20&page=2&size=20
        if (queryString.contains("&")) {
            return "?"+queryString.substring(queryString.indexOf("&") + 1);//obcinamy parametr did, bo inaczej po przekierowaniu znowu będzie wywołana metoda delete
        }else{
            return "";
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {//Rejestrujemy edytory właściwości
        DecimalFormat numberFormat = new DecimalFormat("#0.00");
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
        CustomNumberEditor priceEditor = new CustomNumberEditor(Float.class, numberFormat, true);
        binder.registerCustomEditor(Float.class, "minPrice", priceEditor);
        binder.registerCustomEditor(Float.class, "maxPrice", priceEditor);

    }
}
