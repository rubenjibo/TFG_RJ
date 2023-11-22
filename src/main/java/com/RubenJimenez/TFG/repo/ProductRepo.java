package com.RubenJimenez.TFG.repo;

import com.RubenJimenez.TFG.models.Product;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends ElasticsearchRepository<Product,Integer> {
    @Query("{\n" +
            "        \"bool\":{\n" +
            "            \"must\" : {\n" +
            "                \"terms\": {\n" +
            "                \"category.keyword\": [\"?2\"]\n" +
            "                }\n" +
            "            },\n" +
            "            \"should\" : [\n" +
            "                { \"match\": {\"name\": \"?0\"}},\n" +
            "                { \"match\": {\"desc\": \"?1\"}}\n" +
            "                 \n" +
            "            ],\n" +
            "            \"minimum_should_match\" : 1          \n" +
            "        }       \n" +
            "    }")
    List<Product> findByTextAndCategories(String name, String desc, String[] categories);

    @Query("{\n" +
            "    \"bool\": {\n" +
            "        \"must\": {\n" +
            "            \"terms\": {\n" +
            "                \"category.keyword\": [\"?0\"]\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}")
    List<Product> findCategories(String[] categories);

    List<Product> findByNameContainingOrDescContaining(String name, String desc);
}
/*
* "terms": {"category": ["Estiu"]}
*
* { "match": {"name": "blanca"}},
                { "match": {"desc": "blanca"}}
* */