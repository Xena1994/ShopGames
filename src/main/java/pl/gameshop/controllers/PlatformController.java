package pl.gameshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.gameshop.models.Platform;
import pl.gameshop.services.PlatformService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.Optional;

@Controller
@SessionAttributes("searchCommand")
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    @RequestMapping(value="/platformForm", method= RequestMethod.GET)
    public String showForm(Model model, Optional<Long> id){
        model.addAttribute("platform",
                id.isPresent()?
                        platformService.getPlatform(id.get()):
                        new Platform());
        return "platformForm";
    }

    @RequestMapping(value="/platformForm", method= RequestMethod.POST)
    public String processForm(@Valid @ModelAttribute("platform") Platform p, BindingResult errors) {

        if(errors.hasErrors()){
            return "platformForm";
        }

        platformService.savePlatform(p);

        return "redirect:platforms";//po udanym dodaniu/edycji przekierowujemy na listę
    }

    @RequestMapping(value="/platform", params = "id", method = {RequestMethod.GET})
    public String showProductinPlatformList(Model model, @Param("id") Long id, Pageable pageable){
        model.addAttribute("productListPage", platformService.getAllProductsbyPlatform(id, pageable));
        return "productList";
    }

    @RequestMapping(value="/platforms", method = {RequestMethod.GET})
    public String showCategories(Model model, Pageable pageable){
        model.addAttribute("platformListPage", platformService.getAllPlatforms(pageable));
        return "platformList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/platforms", params={"did"})
    public String deleteCategory(long did, HttpServletRequest request){
        platformService.deletePlatform(did);
        String queryString = prepareQueryString(request.getQueryString());
        return String.format("redirect:platforms%s", queryString);//robimy przekierowanie, ale zachowując parametry pageingu
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
