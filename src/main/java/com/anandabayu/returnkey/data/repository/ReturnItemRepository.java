package com.anandabayu.returnkey.data.repository;

import com.anandabayu.returnkey.data.ReturnItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@Repository
public interface ReturnItemRepository extends JpaRepository<ReturnItem, Long> {
    ReturnItem findReturnItemByIdAndReturnsId(Long id, Long returnsId);

    ReturnItem findReturnItemByReturns_OrderIdAndItemSku(String orderId, String itemId);
}
