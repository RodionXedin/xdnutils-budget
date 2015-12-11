package com.rodionxedin.controller;

import com.rodionxedin.util.SessionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.rodionxedin.util.JsonUtils.failure;
import static com.rodionxedin.util.JsonUtils.success;

/**
 * Created by rodio on 09.12.2015.
 */
@RestController
public class LogoutController {

    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json")
    public String checkLogin() {
        HttpSession session = SessionUtils.getSession();

        Object attribute = session.getAttribute(SessionUtils.SessionAttributes.USER_ATTIBUTE.getAttribute());
        if (attribute != null) {
            session.setAttribute(SessionUtils.SessionAttributes.USER_ATTIBUTE.getAttribute(), null);
            return success().toString();
        }
        return failure().toString();
    }

}
