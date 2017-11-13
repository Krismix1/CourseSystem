package dk.kea.dat16j.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Chris on 13-Nov-17.
 */
@Controller
public class HomeController {

    @GetMapping(path = {"/", "/home"})
    public ModelAndView homePage(HttpServletRequest request) {
        if (request.getUserPrincipal() == null) { // the user is not logged in
            return new ModelAndView("homepage/home-not-signed-in");
        } else {
            return new ModelAndView("homepage/home-signed-in");
        }
    }

    @GetMapping(path = "/testLogin")
    @ResponseBody
    public String testing() {
        return "<h1>If you end up here, then login system has worked</h1>";
    }
}
