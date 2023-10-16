package com.RubenJimenez.TFG.controllers;

import com.RubenJimenez.TFG.models.user;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class userC {
    @RequestMapping(value = "user/{id}")
    public user getUser(@PathVariable int id){
        user persona = new user();
        persona.setNombre("ruben");
        persona.setId(id);
        return persona;
    }

    @RequestMapping(value = "userList")
    public List<user> getUserList(){
        List<user> usuarios = new ArrayList<>();
        user persona1 = new user();
        persona1.setNombre("ruben");
        persona1.setId(1);

        user persona2 = new user();
        persona2.setNombre("andrea");
        persona2.setId(2);

        user persona3 = new user();
        persona3.setNombre("paula");
        persona3.setId(3);
        usuarios.add(persona1);
        usuarios.add(persona2);
        usuarios.add(persona3);
        return usuarios;
    }

    @RequestMapping(value = "userE/{id}")
    public user edit(){
        user persona = new user();
        persona.setNombre("ruben");
        persona.setId(1);
        return persona;
    }

    @RequestMapping(value = "userD/{id}")
    public user delete(){
        user persona = new user();
        persona.setNombre("ruben");
        persona.setId(1);
        return persona;
    }
}
