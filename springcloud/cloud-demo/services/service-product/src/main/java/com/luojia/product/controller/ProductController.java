package com.luojia.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProductController {


    @GetMapping("/product/{id}")
    String getProductById(@PathVariable("id") Long id) {

        return UUID.randomUUID().toString();
    }

}
