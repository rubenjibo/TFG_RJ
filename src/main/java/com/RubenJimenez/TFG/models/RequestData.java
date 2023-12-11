package com.RubenJimenez.TFG.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestData {
    private Event[] eventsProv;
    private Prioritat[] priosProv;

}
