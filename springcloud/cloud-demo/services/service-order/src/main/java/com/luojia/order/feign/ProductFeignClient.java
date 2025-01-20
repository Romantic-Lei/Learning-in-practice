package com.luojia.order.feign;

import com.luojia.order.feign.fallback.ProductFeginClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-product", fallback = ProductFeginClientFallback.class)
public interface ProductFeignClient {

    @GetMapping("/product/{id}")
    String getProductById(@PathVariable("id") Long id);

}
