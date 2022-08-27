package com.anandabayu.returnkey.data.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@Data
public class BodyReturnStatus {
    @NotNull
    @Pattern(regexp = "ACCEPTED|REJECTED")
    private String status;
}
