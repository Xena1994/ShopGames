package pl.gameshop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import pl.gameshop.repositories.OrderItemRepository;
import pl.gameshop.repositories.OrderRepository;
import pl.gameshop.repositories.ProductRepository;
import pl.gameshop.repositories.UserRepository;
import pl.gameshop.models.Order;
import pl.gameshop.models.OrderItem;
import pl.gameshop.models.Product;
import pl.gameshop.services.ShoppingCartService;

import java.math.BigDecimal;
import java.util.*;


@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;

    private Map<Product, Integer> products = new HashMap<>();

    private final String SUBJECT = "Order confirmation  ";


    @Override
    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }

    @Override
    public void removeProduct(Product product) {
        for(Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product productEntry = entry.getKey();
            Integer count = entry.getValue();
            if (productEntry.getId().equals(product.getId())) {
                if (count > 1) {
                    products.replace(product, products.get(product) - 1);
                } else {
                    products.remove(productEntry);
                }
                return;
            }
        }
    }

    @Override
    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    @Override
    public void checkout() {
        Order order = new Order();
        order.setItems(new ArrayList<OrderItem>());
        order.setOrderDate(new Date());
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            OrderItem item = new OrderItem();
            item.setName(entry.getKey().getName());
            item.setPrice(entry.getKey().getPrice());
            orderItemRepository.save(item);
            order.getItems().add(item);
        }
        order.setTotal(getTotal());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        order.setUser(userRepository.findByUsername(userName));
        orderRepository.save(order);
        orderRepository.flush();
        products.clear();


    }

    @Override
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            total = total.add(entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())));
        }
        return total;
    }

}
