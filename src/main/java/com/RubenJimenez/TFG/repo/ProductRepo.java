package com.RubenJimenez.TFG.repo;

import com.RubenJimenez.TFG.models.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepo extends ElasticsearchRepository<Product,Integer> {

}
