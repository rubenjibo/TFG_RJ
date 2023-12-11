package com.RubenJimenez.TFG.service;


import com.RubenJimenez.TFG.models.Product;

import com.RubenJimenez.TFG.repo.ProductRepo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Sort;


import java.util.Arrays;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {


    @Autowired
    private ProductRepo productRepo;

    public Iterable<Product> getProducts(){
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        return productRepo.findAll(sort);
    }

    public Iterable<Product> filterProducts(String searchText, String[] categories){
        if (categories.length == 0){
            return productRepo.findByNameContainingOrDescContaining(searchText, searchText);

        } else if (searchText.isBlank()) {
            String formattedCategories = Arrays.stream(categories)
                    .map(category -> "\"" + category + "\"")
                    .collect(Collectors.joining(", "));

            System.out.println(formattedCategories);

            return productRepo.findCategories(categories);
        }else{
            System.out.println(Arrays.toString(categories));

            return productRepo.findByTextAndCategories(searchText, searchText, categories);

        }

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
