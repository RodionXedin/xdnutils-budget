package com.rodionxedin.controller;

import com.rodionxedin.db.UserRepository;
import com.rodionxedin.model.User;
import com.rodionxedin.service.machine.TimeMachine;
import com.rodionxedin.service.machine.TimeMachineUserEntry;
import com.rodionxedin.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.rodionxedin.util.JsonUtils.*;

/**
 * Created by rodio on 09.12.2015.
 */
@RestController
public class LoginController {


    @Autowired
    private TimeMachine timeMachine;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
    public String checkLogin() {
        HttpSession session = SessionUtils.getSession();

        User user = (User) session.getAttribute(SessionUtils.SessionAttributes.USER_ATTIBUTE.getAttribute());
        if (user != null) {
            return addBasicUserInfo(success(), user).toString();
        }


        return failure().toString();
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public String login(@RequestParam(value = "name", required = false) String name,
                        @RequestParam(value = "password", required = false) String password, @RequestParam Map<String, String> params) {

        User user = userRepository.findByName(name);

        HttpSession session = SessionUtils.getSession();

        boolean newUser = false;
        if (user != null) {
            if (user.getPassword().equals(password)) {
                session.setAttribute(SessionUtils.SessionAttributes.USER_ATTIBUTE.getAttribute(), user);
            } else {
                return addError(failure(), "wrong password").toString();
            }
        }


        if (user == null) {
            user = new User(name, password);
            userRepository.save(user);
            newUser = true;
        }

        session.setAttribute(SessionUtils.SessionAttributes.USER_ATTIBUTE.getAttribute(), user);
        timeMachine.getTimeMachineUserEntryMap().put(user, new TimeMachineUserEntry(user));

        return addBasicUserInfo(success(), user, newUser).toString();
    }
}
