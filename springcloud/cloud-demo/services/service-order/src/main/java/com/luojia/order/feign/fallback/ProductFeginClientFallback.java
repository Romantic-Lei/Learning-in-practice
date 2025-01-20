package com.luojia.order.feign.fallback;


import com.luojia.order.feign.ProductFeignClient;
import org.springframework.stereotype.Component;

/**
 * 服务接口兜底，他必须要配合熔断服务，比如sentinel
 */
@Component
public class ProductFeginClientFallback implements ProductFeignClient {
    @Override
    public String getProductById(Long id) {
        System.out.println("兜底回调...");

        return null;
    }
}
