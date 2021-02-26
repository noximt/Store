package by.company.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/profile")
public class UserController {

    @GetMapping
    public ModelAndView getProfilePage(ModelAndView modelAndView){
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @PostMapping(path = "/logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession session){
        session.invalidate();
        modelAndView.setViewName("redirect:/authorize");
        return modelAndView;
    }
}
