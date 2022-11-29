package pl.gameshop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.gameshop.models.Order;

public interface OrderService {
    Page<Order> getAllOrdersByActiveUser(Pageable pageable);

    Page<Order> getAllOrders(Pageable pageable);

    Order getOrder(Long id);

    void deleteOrder(Long id);
}
