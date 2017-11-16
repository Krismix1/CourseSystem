package dk.kea.dat16j.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Chris on 13-Nov-17.
 */
@Controller
public class HomeController {

    public static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @GetMapping(path = {"/", "/home"})
    public ModelAndView homePage(HttpServletRequest request) {
        if (request.getUserPrincipal() == null) { // the user is not logged in
            LOG.debug("Guest has connected.");
            return new ModelAndView("homepage/home-not-signed-in");
        } else {
            return new ModelAndView("homepage/home-signed-in");
        }
    }
}
