package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/balance")
//    public Balance getBalance(@RequestParam Long userId) {
//        // Retrieve the user by ID
//        UserRecord user = userRepository.findById(userId).orElse(null);
//
//        // Return a Balance object with user's balance, or 0 if user not found
//        if (user != null) {
//            return new Balance(user.getId(), user.getBalance());
//        } else {
//            return new Balance(userId, 0); // Return balance 0 for non-existent user
//        }
//    }
    public Balance getBalance(@RequestParam Long userId) {
        return userRepository.findById(userId)
                .map(user -> new Balance(user.getBalance()))
                .orElse(new Balance(0.0f));
    }
}
