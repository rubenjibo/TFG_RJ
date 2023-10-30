package com.RubenJimenez.TFG.controllers;

import com.RubenJimenez.TFG.models.Product;
import com.RubenJimenez.TFG.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/prod")
public class productC {

    @Autowired
    private ProductService productService;
    @GetMapping("/findAll")
    public Iterable<Product> findAll(){
        return productService.getProducts();
    }

    @PostMapping("/insert")
    public Product insertProduct(@RequestBody Product prod){

        return productService.insertProducts(prod);

    }
    @PostMapping("/delete")
    public Optional<Product> deleteProduct(@RequestBody int id){

        return productService.deleteProduct(id);

    }

    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product prod) {
        return productService.updateProduct(prod);
    }
}
