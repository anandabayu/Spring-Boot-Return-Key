package com.anandabayu.returnkey.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@Data
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item {
    @Id
    @Column(name = "sku")
    private String sku;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;
}
