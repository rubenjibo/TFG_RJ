package com.RubenJimenez.TFG.controllers;

import com.RubenJimenez.TFG.models.Product;
import com.RubenJimenez.TFG.service.EventService;
import com.RubenJimenez.TFG.service.PrioritatService;
import com.RubenJimenez.TFG.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private EventService eventService;

    @Autowired
    private PrioritatService prioService;



    @GetMapping("/findAll")
    public Iterable<Product> findAll(){
        return prioService.sortProductsOnPrio(
                eventService.sortProductsOnEvents(
                productService.getProducts()));

        /*return eventService.sortProductsOnEvents(
                        productService.getProducts());*/

    }

    @GetMapping("/filterProducts")
    public Iterable<Product> filterProducts(@RequestParam String searchText, String[] categories){
        return eventService.sortProductsOnEvents(productService.filterProducts(searchText,categories));
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
