package com.anandabayu.returnkey.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@Data
@NoArgsConstructor
@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "email")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @JsonIgnoreProperties({"id", "order"})
    private List<OrderItem> orderItems;
}
