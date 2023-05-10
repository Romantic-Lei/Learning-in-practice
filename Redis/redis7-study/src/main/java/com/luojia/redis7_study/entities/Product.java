package com.luojia.redis7_study.entities;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="聚划算活动Product信息")
public class Product {

    // 产品id
    private Long id;
    // 产品名称
    private String name;
    // 产品价格
    private Integer price;
    // 产品详情
    private String detail;

}
