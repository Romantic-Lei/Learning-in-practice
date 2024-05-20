package com.luojiapay.payment.controller;

import com.luojiapay.payment.entity.Product;
import com.luojiapay.payment.service.ProductService;
import com.luojiapay.payment.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Api(tags = "商品管理")
@CrossOrigin // 开放前端跨域访问
@RestController
@RequestMapping("/api/product")
public class productController {

    @Autowired
    ProductService productService;

    @ApiOperation("测试接口")
    @GetMapping("/test")
    public Result test() {
        return Result.ok().data("message", "hello").data("now", new Date());
    }

    @ApiOperation("商品列表接口")
    @GetMapping("/list")
    public Result list() {
        List<Product> list = productService.list();
        return Result.ok().data("productList", list);
    }

}
