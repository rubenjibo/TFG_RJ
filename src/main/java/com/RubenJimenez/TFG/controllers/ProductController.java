package com.RubenJimenez.TFG.controllers;

import com.RubenJimenez.TFG.models.Event;
import com.RubenJimenez.TFG.models.Prioritat;
import com.RubenJimenez.TFG.models.Product;
import com.RubenJimenez.TFG.models.RequestData;
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
                productService.getProducts())
        );
    }

    @PostMapping("/findAllProv")
    public Iterable<Product> findAllProv(@RequestBody RequestData requestData){


        Event[] eventsProv = requestData.getEventsProv();
        Prioritat[] priosProv = requestData.getPriosProv();

        if(eventsProv.length == 0  && priosProv.length != 0 ){
            return prioService.sortProductsOnPrioProv(priosProv,
                    eventService.sortProductsOnEvents(
                            productService.getProducts())
            );
        } else if (eventsProv.length != 0 && priosProv.length == 0 ) {
            return prioService.sortProductsOnPrio(
                    eventService.sortProductsOnEventsProv(eventsProv,
                            productService.getProducts())
            );
        } else if (eventsProv.length == 0 && priosProv.length == 0) {

            return prioService.sortProductsOnPrio(
                    eventService.sortProductsOnEvents(
                            productService.getProducts())
            );

        } else {
            return prioService.sortProductsOnPrioProv(priosProv,
                    eventService.sortProductsOnEventsProv(eventsProv,
                            productService.getProducts())
            );
        }





    }

    @GetMapping("/filterProducts")
    public Iterable<Product> filterProducts(@RequestParam String searchText, String[] categories){
        return productService.filterProducts(searchText,categories);
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


