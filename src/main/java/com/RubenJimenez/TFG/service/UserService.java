package com.RubenJimenez.TFG.service;

import com.RubenJimenez.TFG.models.Prioritat;
import com.RubenJimenez.TFG.models.User;
import com.RubenJimenez.TFG.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public User insertUser(User user){

        String encodedPassword = passwordEncoder.encode(user.getPwd());
        user.setPwd(encodedPassword);
        return userRepo.save(user);
    }

    public void deleteUser(User user){
        userRepo.delete(user);
    }

    public Boolean checkUser(User user) {
        Optional<User> userBD = userRepo.findByName(user.getName());
        if (userBD.isPresent()) {
            User existingUser = userBD.get();

            return passwordEncoder.matches(user.getPwd(), existingUser.getPwd());
        }
        return false;
    }
}
