package com.springproject.droolEngineProject.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

    private String name;
    private String cardType;
    private int discount;
    private Integer price;

}
