package com.RubenJimenez.TFG.service;

import com.RubenJimenez.TFG.models.Event;
import com.RubenJimenez.TFG.models.Product;
import com.RubenJimenez.TFG.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class EventService {
    @Autowired
    private EventRepo eventRepo;

    public Iterable<Event> getEvents(){
        return eventRepo.findAll();
    }

    public Event insertEvents(Event event){

        return eventRepo.save(event);
    }

    public Iterable<Product> sortProductsOnEvents(Iterable<Product> prods){



        Iterable<Event> events = getEvents();

        List<Event> eventsAux = new ArrayList<>();
        for (Event e : events) {
            eventsAux.add(e);
        }


        List<Product> prodsAux = new ArrayList<>();
        for (Product p : prods) {
            prodsAux.add(p);

        }
        for(Product pr:prods){
            System.out.println("ID: " +pr.getId()+ " Name: " + pr.getName());
        }
        System.out.println(prodsAux);
        List<Boolean> prio = new ArrayList<>();

        for(int a = 0; a < prodsAux.size();a++){
            prio.add(false);
        }

        for (int i = 0 ; i < eventsAux.size() ; i++){

            String category = eventsAux.get(i).getCategoria();


            for(int y = 0; y < prodsAux.size();y++){

                String[] categoryesProd = prodsAux.get(y).getCategory();


                for (String categ : categoryesProd) {
                    if (categ.equals(category)) {
                        prio.set(y,true);

                    }
                }

            }
        }

        List<Product> prodsEvent = new ArrayList<>();

        List<Product> prodsNoEvent = new ArrayList<>();

        for (int z = 0; z < prodsAux.size();z++) {
            if(prio.get(z)){
                prodsEvent.add(prodsAux.get(z));

            }else{
                prodsNoEvent.add(prodsAux.get(z));
            }

        }
        System.out.println(prio);
        prodsEvent.addAll(prodsNoEvent);

        return prodsEvent;
    }
}
