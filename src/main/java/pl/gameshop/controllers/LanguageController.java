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
import pl.gameshop.models.Language;
import pl.gameshop.services.LanguageService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.Optional;

@Controller
@SessionAttributes("searchCommand")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @RequestMapping(value="/languageForm", method= RequestMethod.GET)
    public String showForm(Model model, Optional<Long> id){
        model.addAttribute("language",
                id.isPresent()?
                        languageService.getLanguage(id.get()):
                        new Language());
        return "languageForm";
    }

    @RequestMapping(value="/languageForm", method= RequestMethod.POST)
    public String processForm(@Valid @ModelAttribute("language") Language language, BindingResult errors) {

        if(errors.hasErrors()){
            return "languageForm";
        }

        languageService.saveLanguage(language);

        return "redirect:languages";//po udanym dodaniu/edycji przekierowujemy na listę
    }

    @RequestMapping(value="/languages", method = {RequestMethod.GET})
    public String showLanguages(Model model, Pageable pageable){
        model.addAttribute("languageListPage", languageService.getAllLanguages(pageable));
        return "languageList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/languages", params={"did"})
    public String deleteLanguage(long did, HttpServletRequest request){
        languageService.deleteLanguage(did);
        String queryString = prepareQueryString(request.getQueryString());
        return String.format("redirect:languages%s", queryString);//robimy przekierowanie, ale zachowując parametry pageingu
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
