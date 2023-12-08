package com.RubenJimenez.TFG.service;

import com.RubenJimenez.TFG.models.Prioritat;
import com.RubenJimenez.TFG.models.Product;
import com.RubenJimenez.TFG.repo.PrioritatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrioritatService {

    @Autowired
    private PrioritatRepo prioRepo;

    public Iterable<Prioritat> getPrios(){

        return prioRepo.findAll();
    }

    public Prioritat insertPrio(Prioritat prio){

        if (prioRepo.existsByPosition(prio.getPosition())) {
            // Manejar el caso, por ejemplo, lanzando una excepción o devolviendo null
            throw new IllegalStateException("Ya existe una prioridad con la posición: " + prio.getPosition());
        }

        return prioRepo.save(prio);
    }

    public void deletePrio(Prioritat prio){
        prioRepo.delete(prio);
    }

    public Iterable<Product> sortProductsOnPrio(Iterable<Product> prods){

        LocalDate fechaHoyNoFormat = LocalDate.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateTodayAux = fechaHoyNoFormat.format(formateador);
        LocalDate dateToday = LocalDate.parse(dateTodayAux, formateador);

        Iterable<Prioritat> prioritats = getPrios();



        List<Prioritat> prioritatsAux = new ArrayList<>();
        List<Integer> indexReserved = new ArrayList<>();
        List<Integer> prodReserved = new ArrayList<>();
        for(Prioritat p:prioritats){

            //comprobar si estan on date

            LocalDate dateIni = LocalDate.parse(ajustarFormatoFecha(p.getDate_ini()), formateador);
            LocalDate dateFi = LocalDate.parse(ajustarFormatoFecha(p.getDate_fi()), formateador);

            if(dateToday.isAfter(dateIni) && dateToday.isBefore(dateFi)) {

                prioritatsAux.add(p);
                indexReserved.add(p.getPosition());
                prodReserved.add(p.getProduct());

            }

        }



        List<Product> prodsPrio = new ArrayList<>();
        List<Product> prodsNoPrio = new ArrayList<>();
        for (Product p : prods) {
            if(prodReserved.contains(p.getId())){
                prodsPrio.add(p);
            }else{
                prodsNoPrio.add(p);
            }

        }

        System.out.println(prodsPrio);
        System.out.println(prodsNoPrio);

        var totalLen=prodsPrio.size() + prodsNoPrio.size();
        List<Product> prodsAux = new ArrayList<>();
        var y=0;
        for(int i=0; i < totalLen; i++){

            if(indexReserved.contains(i)){

                var indexAux=indexReserved.indexOf(i);
                var prodid=prodReserved.get(indexAux);
                for (Product p2:prodsPrio){
                    if(p2.getId()==prodid){
                        prodsAux.add(p2);
                    }
                }

            }else{
                prodsAux.add(prodsNoPrio.get(y));
                y++;
            }

        }

        return prodsAux;
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
