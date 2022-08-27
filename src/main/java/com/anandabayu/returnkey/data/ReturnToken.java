package com.anandabayu.returnkey.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@Data
@NoArgsConstructor
@Entity
@Table(name = "return_token")
public class ReturnToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "token")
    private String token;

    public ReturnToken(Order order, String token) {
        this.order = order;
        this.token = token;
    }
}
