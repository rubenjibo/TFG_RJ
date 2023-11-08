package com.RubenJimenez.TFG.models;

import com.RubenJimenez.TFG.util.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "prod")
public class Product {
    @Id
    private int id;
    private String name;
    private String img;
    private String desc;




    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")

    //@JsonDeserialize(using = CustomDateDeserializer.class)
    @Field(type = FieldType.Date, format = DateFormat.date)
    private String date;

    /*@Field(type = FieldType.Date, store = true, format = DateFormat.year_month_day)
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
    private Date date;*/

    private String[] category;
    private float price;



}
