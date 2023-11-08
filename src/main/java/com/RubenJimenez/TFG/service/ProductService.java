package com.RubenJimenez.TFG.service;

import com.RubenJimenez.TFG.models.Product;

import com.RubenJimenez.TFG.repo.ProductRepo;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Sort;
import java.util.Optional;

@Service
public class ProductService {


    @Autowired
    private ProductRepo productRepo;

    public Iterable<Product> getProducts(){
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        return productRepo.findAll(sort);
    }

    public Product insertProducts(Product prod){

        return productRepo.save(prod);
    }

    public Product updateProduct(Product prod) {

        if(productRepo.existsById(prod.getId())) {
            return productRepo.save(prod);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found"
            );
        }
    }

    public Optional<Product> deleteProduct(int id) {

        Optional<Product> product = productRepo.findById(id);

        if (product.isPresent()) {
            productRepo.deleteById(id);
        }

        return product;
    }

}
