package by.company.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/item")
public class ItemController {

    @PostMapping (path = "/newItems")
    public ModelAndView newItems(ModelAndView modelAndView, HttpSession httpSession) {
        modelAndView.setViewName("newItem");
        return modelAndView;
    }
}
