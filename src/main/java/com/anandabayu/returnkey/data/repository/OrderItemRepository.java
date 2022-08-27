package com.anandabayu.returnkey.data.repository;

import com.anandabayu.returnkey.data.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findOrderItemByOrderIdAndItemSku(String orderId, String sku);

    List<OrderItem> findOrderItemsByOrderId(String orderId);
}
