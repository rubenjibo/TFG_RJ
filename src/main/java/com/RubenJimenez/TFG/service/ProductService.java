package com.RubenJimenez.TFG.service;

import com.RubenJimenez.TFG.models.Product;
import com.RubenJimenez.TFG.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public Iterable<Product> getProducts(){

        return productRepo.findAll();
    }

    public Product insertProducts(Product prod){

        return productRepo.save(prod);
    }

    public Product updateProduct(Product prod,int id){

        Product prod1 = productRepo.findById(id).get();
        prod1.setPrice(prod.getPrice());
        return prod1;
    }

    public int deleteProduct(int id){
        productRepo.deleteById(id);
        return 1;
    }

}
