package com.RubenJimenez.TFG.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "event")
public class Event {

    private String categoria;

    @Field(type = FieldType.Date, format = DateFormat.date)
    private String date_ini;

    @Field(type = FieldType.Date, format = DateFormat.date)
    private String date_fi;





}