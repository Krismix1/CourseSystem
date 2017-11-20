package dk.kea.dat16j.controllers;

import dk.kea.dat16j.models.AccountRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Chris on 13-Nov-17.
 */
@Controller
public class HomeController {

    @GetMapping(path = {"/", "/home"})
    public ModelAndView homePage(HttpServletRequest request) {
        if (request.getUserPrincipal() == null) { // the user is not logged in
            return new ModelAndView("homepage/home-not-signed-in");
        }
        if (request.isUserInRole(AccountRoles.STUDENT.getRole())) {
            return new ModelAndView("student/student-homepage");
        }
        if (request.isUserInRole(AccountRoles.TEACHER.getRole())) {
            return new ModelAndView("teacher/teacher-homepage");
        }

        return new ModelAndView("homepage/home-signed-in");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        // This is not good for unit testing, using the SecurityContextHolder.getContext().getAuthentication()
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
//        return "redirect:/";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
}
