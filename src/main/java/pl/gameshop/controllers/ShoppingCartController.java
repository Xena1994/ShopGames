package pl.gameshop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.gameshop.services.ProductService;
import pl.gameshop.services.ShoppingCartService;

@Controller
public class ShoppingCartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/order")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("order");
        modelAndView.addObject("products", shoppingCartService.getProductsInCart());
        modelAndView.addObject("total", shoppingCartService.getTotal());
        return modelAndView;
    }

    @GetMapping(value = "/order/addProduct", params = "id")
    public ModelAndView addProductToCart(@Param("id") Long id) {
        shoppingCartService.addProduct(productService.getProduct(id));
        return shoppingCart();
    }

    @GetMapping(value = "/order/removeProduct", params = "id")
    public ModelAndView removeProductFromCart(@Param("id") Long id) {
        shoppingCartService.removeProduct(productService.getProduct(id));
        return shoppingCart();
    }

    @GetMapping("/order/checkout")
    public ModelAndView checkout() {
        shoppingCartService.checkout();
        return shoppingCart();
    }



}
