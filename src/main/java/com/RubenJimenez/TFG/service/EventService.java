package com.RubenJimenez.TFG.service;

import com.RubenJimenez.TFG.models.Event;
import com.RubenJimenez.TFG.models.Product;
import com.RubenJimenez.TFG.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    public void deleteEvent(Event event) {

        eventRepo.delete(event);
    }

    public Iterable<Product> sortProductsOnEvents(Iterable<Product> prods){

        LocalDate fechaHoyNoFormat = LocalDate.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateTodayAux = fechaHoyNoFormat.format(formateador);
        LocalDate dateToday = LocalDate.parse(dateTodayAux, formateador);

        System.out.println(dateToday);

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

            LocalDate dateIni = LocalDate.parse(ajustarFormatoFecha(eventsAux.get(i).getDate_ini()), formateador);
            LocalDate dateFi = LocalDate.parse(ajustarFormatoFecha(eventsAux.get(i).getDate_fi()), formateador);

            if (dateToday.isAfter(dateIni) && dateToday.isBefore(dateFi)) {

                for(int y = 0; y < prodsAux.size();y++){

                    String[] categoryesProd = prodsAux.get(y).getCategory();


                    for (String categ : categoryesProd) {
                        if (categ.equals(category)) {
                            prio.set(y,true);

                        }
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

    public static String ajustarFormatoFecha(String fecha) {
        String[] partes = fecha.split("-");

        // Asegurarse de que el año, mes y día están presentes
        if (partes.length < 3) {
            throw new IllegalArgumentException("Formato de fecha inválido");
        }

        String year = partes[0];
        String month = partes[1];
        String day = partes[2];

        // Añadir un 0 si el mes o el día tienen un solo dígito

        if (day.length() == 1) {
            day = "0" + day;
        }

        return year + "-" + month + "-" + day;
    }

}
