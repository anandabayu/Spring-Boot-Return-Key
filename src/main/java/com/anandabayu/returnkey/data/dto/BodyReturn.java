package com.anandabayu.returnkey.data.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@Data
public class BodyReturn {
    @NotNull
    private String token;

    private List<BodyReturnItem> items;
}
