package com.RubenJimenez.TFG.controllers;

import com.RubenJimenez.TFG.models.Product;
import com.RubenJimenez.TFG.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis")
public class productC {

    @Autowired
    private ProductService productService;
    @GetMapping("/findAll")
    Iterable<Product> findAll(){
        return productService.getProducts();
    }
}
