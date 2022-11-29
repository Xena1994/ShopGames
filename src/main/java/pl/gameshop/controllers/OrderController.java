package pl.gameshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.gameshop.services.OrderService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value="/orders", method = {RequestMethod.GET})
    public String showOrder(Model model, Pageable pageable){
        model.addAttribute("orderListPage", orderService.getAllOrders(pageable));
        return "orders";
    }

    @RequestMapping(value="/myOrders", method = {RequestMethod.GET})
    public String showCategories(Model model, Pageable pageable){
        model.addAttribute("orderListPage", orderService.getAllOrdersByActiveUser(pageable));
        return "myOrders";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/orders", params={"did"})
    public String deleteCategory(long did, HttpServletRequest request){
        orderService.deleteOrder(did);
        return "redirect:orders";
    }



}
