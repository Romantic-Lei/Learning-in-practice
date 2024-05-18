package com.luojiapay.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luojiapay.payment.entity.Product;
import com.luojiapay.payment.mapper.ProductMapper;
import com.luojiapay.payment.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
