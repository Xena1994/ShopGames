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
import pl.gameshop.controllers.commands.ProductFilter;
import pl.gameshop.models.Category;
import pl.gameshop.models.Platform;
import pl.gameshop.services.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.List;

@Controller
@SessionAttributes(names={"searchCommand"})
public class ProductListController {

    @Autowired
    private ProductService productService;

    @ModelAttribute("platformList")
    public List<Platform> loadPlatforms(){
        List<Platform> platforms = productService.getAllPlatforms();
        return platforms;
    }
    @ModelAttribute("categoriesList")
    public List<Category> loadCategories(){
        List<Category> categories = productService.getAllCategories();
        return categories;
    }

    @RequestMapping(value="/products", params = "id", method = RequestMethod.GET)
    public String showProductDetails(Model model, Long id){
        model.addAttribute("product", productService.getProduct(id));
        return "product";
    }

    @GetMapping(value="/error")
    public String resetProductList(){
        return "redirect:products";
    }


    @ModelAttribute("searchCommand")
    public ProductFilter getSimpleSearch(){
        return new ProductFilter();
    }

    @GetMapping(value="/products", params = {"all"})
    public String resetProductList(@ModelAttribute("searchCommand") ProductFilter search){
        search.clear();
        return "redirect:products";
    }

    @RequestMapping(value="/products", method = {RequestMethod.GET, RequestMethod.POST})
    public String showProductList(Model model, @Valid @ModelAttribute("searchCommand") ProductFilter search,
                                  BindingResult result, Pageable pageable){
        model.addAttribute("productListPage", productService.getAllProducts(search, pageable));
        return "productList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/products", params={"did"})
    public String deleteProduct(long did, HttpServletRequest request){
        productService.deleteProduct(did);
        String queryString = prepareQueryString(request.getQueryString());
        return String.format("redirect:products%s", queryString);//robimy przekierowanie, ale zachowując parametry pageingu
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
