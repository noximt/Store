package by.company.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public ModelAndView mainPage(ModelAndView modelAndView) {
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView search(ModelAndView modelAndView, @RequestParam String itemToBeFound){
        modelAndView.setViewName("index");
        if (itemToBeFound.equals("test")){
            modelAndView.setViewName("redirect:/authorize");
        }
        return modelAndView;
    }
}
