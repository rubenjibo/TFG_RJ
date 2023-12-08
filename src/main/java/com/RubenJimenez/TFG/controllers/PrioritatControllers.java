package com.RubenJimenez.TFG.controllers;




import com.RubenJimenez.TFG.models.Prioritat;
import com.RubenJimenez.TFG.service.PrioritatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prio")
public class PrioritatControllers {

    @Autowired
    PrioritatService prioService;

    @GetMapping("/findAll")
    public Iterable<Prioritat> findAll(){

        return prioService.getPrios();
    }

    @PostMapping("/insert")
    public Prioritat insertPrio(@RequestBody Prioritat prio){

        return prioService.insertPrio(prio);
    }

    @PostMapping("/delete")
    public void deletePrio(@RequestBody Prioritat prio){

         prioService.deletePrio(prio);
    }
}
