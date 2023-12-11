package com.RubenJimenez.TFG.controllers;


import com.RubenJimenez.TFG.models.User;
import com.RubenJimenez.TFG.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/insert")
    public void insertUser(@RequestBody User user){
        System.out.println(user);
        userService.insertUser(user);
    }

    @PostMapping("/delete")
    public void deleteUser(@RequestBody User user){
        userService.deleteUser(user);
    }

    @PostMapping("/checkUser")
    public Boolean checkUser(@RequestBody User user){

        return userService.checkUser(user);
    }

}
