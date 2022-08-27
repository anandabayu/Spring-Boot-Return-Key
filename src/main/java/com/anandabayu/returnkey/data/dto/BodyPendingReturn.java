package com.anandabayu.returnkey.data.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@Data
public class BodyPendingReturn {

    @NotNull
    private String orderId;

    @NotNull
    private String email;

}
