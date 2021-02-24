package by.company.controllers;

import by.company.DTOs.UserAuthDto;
import by.company.domains.Response;
import by.company.domains.User;
import by.company.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/authorize")
public class AuthorizationController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView getAuthorizationPage(ModelAndView modelAndView){
        modelAndView.setViewName("authorization");
        modelAndView.addObject("user", new UserAuthDto());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView authorize(@ModelAttribute("user") UserAuthDto userAuthDto, ModelAndView modelAndView, HttpSession session){
        Response<User> byUsername = userService.getByUsername(userAuthDto.getUsername());
        if (byUsername.isBool()){
            User user = byUsername.getElement();
            if (user.getPassword().equals(userAuthDto.getPassword())){
                modelAndView.setViewName("/profile");
            }else {
                //отображение проблемы связанной с паролем на странице авторизации
                //
                //
                modelAndView.setViewName("redirect:/authorize");
            }
        }else{
            //отображение проблемы связанной с username на странице авторизации
            //
            //
            modelAndView.setViewName("redirect:/authorize");
        }
        return modelAndView;
    }
}
