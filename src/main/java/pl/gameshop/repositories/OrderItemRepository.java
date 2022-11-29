package pl.gameshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gameshop.models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
