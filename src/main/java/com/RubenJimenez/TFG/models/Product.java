package com.RubenJimenez.TFG.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "products")
public class Product {
    @Id
    private int id;
    private String name;
    private String img;
    private String desc;
    private String date;
    private String[] category;
    private int price;



}
/*
 {
"id": 1,
"name": "cadira",
"img": "none",
"desc": "desc",
"date":"date",
"category":["nadal","estiu"],
"price": 10

}
 */