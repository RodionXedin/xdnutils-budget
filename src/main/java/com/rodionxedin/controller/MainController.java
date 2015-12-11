package com.rodionxedin.controller;

import com.rodionxedin.db.UserRepository;
import com.rodionxedin.model.Change;
import com.rodionxedin.model.User;
import com.rodionxedin.model.Wallet;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

/**
 * Created by rodio on 08.12.2015.
 */
@Controller
public class MainController {


    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/")
    public String serveMain(Model model) {

        userRepository.findByName("testuser1");
        User testuser1 = userRepository.findByName("testuser1");
        model.addAttribute("testuser", testuser1);
        return "main";
    }

}
