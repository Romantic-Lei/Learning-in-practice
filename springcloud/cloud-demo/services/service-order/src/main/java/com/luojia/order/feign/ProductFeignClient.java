package com.luojia.order.feign;

import com.luojia.order.feign.fallback.ProductFeginClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "service-product", fallback = ProductFeginClientFallback.class)
public interface ProductFeignClient {

    @GetMapping("/api/product/product/{id}")
    String getProductById(@PathVariable("id") Long id);

}
