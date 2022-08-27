package com.anandabayu.returnkey.service;

import com.anandabayu.returnkey.data.Order;
import com.anandabayu.returnkey.data.OrderItem;
import com.anandabayu.returnkey.data.ReturnItem;
import com.anandabayu.returnkey.data.ReturnToken;
import com.anandabayu.returnkey.data.Returns;
import com.anandabayu.returnkey.data.repository.OrderItemRepository;
import com.anandabayu.returnkey.data.repository.OrderRepository;
import com.anandabayu.returnkey.data.repository.ReturnItemRepository;
import com.anandabayu.returnkey.data.repository.ReturnTokenRepository;
import com.anandabayu.returnkey.data.repository.ReturnsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@AllArgsConstructor
@Transactional
@Service
public class ReturnService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ReturnsRepository returnsRepository;
    private final ReturnItemRepository returnItemRepository;
    private final ReturnTokenRepository returnTokenRepository;

    public Order findOrderByIdAndEmail(String id, String email) {
        return orderRepository.findOrderByIdAndEmail(id, email);
    }

    public OrderItem findOrderItemByOrderIdAndItemSku(String orderId, String sku) {
        return orderItemRepository.findOrderItemByOrderIdAndItemSku(orderId, sku);
    }

    public List<OrderItem> findOrderItemsByOrderId(String orderId) {
        return orderItemRepository.findOrderItemsByOrderId(orderId);
    }

    public ReturnToken saveReturnToken(ReturnToken returnToken) {
        return returnTokenRepository.save(returnToken);
    }

    public ReturnToken findReturnTokenByToken(String token) {
        return returnTokenRepository.findReturnTokenByToken(token);
    }

    public void deleteReturnToken(ReturnToken returnTOken) {
        returnTokenRepository.delete(returnTOken);
    }

    public Returns saveReturns(Returns returns) {
        return returnsRepository.save(returns);
    }

    public Optional<Returns> findReturnsById(Long id) {
        return returnsRepository.findById(id);
    }

    public ReturnItem findReturnItemByIdAndReturnsId(Long itemId, Long returnsId) {
        return returnItemRepository.findReturnItemByIdAndReturnsId(itemId, returnsId);
    }

    public ReturnItem saveReturnItem(ReturnItem returnItem) {
        return returnItemRepository.save(returnItem);
    }

    public ReturnItem findReturnItemByReturns_OrderIdAndItemSku(String orderId, String itemId) {
        return returnItemRepository.findReturnItemByReturns_OrderIdAndItemSku(orderId, itemId);
    }
}
