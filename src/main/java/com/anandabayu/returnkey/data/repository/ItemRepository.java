package com.anandabayu.returnkey.data.repository;

import com.anandabayu.returnkey.data.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    Item findItemBySkuAndName(String sku, String name);
}
