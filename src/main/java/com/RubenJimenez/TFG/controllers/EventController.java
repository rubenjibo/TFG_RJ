package com.RubenJimenez.TFG.controllers;


import com.RubenJimenez.TFG.models.Event;
import com.RubenJimenez.TFG.models.Product;
import com.RubenJimenez.TFG.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;
    @GetMapping("/findAll")
    public Iterable<Event> findAll(){

        return eventService.getEvents();
    }

    @PostMapping("/insert")
    public Event insertEvent(@RequestBody Event event){

        return eventService.insertEvents(event);
    }

    @PostMapping("/delete")
    public void deleteEvent(@RequestBody Event event){

         eventService.deleteEvent(event);
    }
}
