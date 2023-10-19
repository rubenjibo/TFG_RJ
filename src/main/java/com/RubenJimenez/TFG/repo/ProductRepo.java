package com.RubenJimenez.TFG.repo;

import com.RubenJimenez.TFG.models.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends ElasticsearchRepository<Product,Integer> {

}
