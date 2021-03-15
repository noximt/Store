package by.company.controllers;

import by.company.DTOs.UserDto;
import by.company.domains.User;
import by.company.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(path = "/profile")
public class UserController {
    @Autowired

    UserService userService;

    @GetMapping
    public ModelAndView getProfilePage(ModelAndView modelAndView) {
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @PostMapping(path = "/logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession session) {
        session.invalidate();
        modelAndView.setViewName("redirect:/authorize");
        return modelAndView;
    }

    @GetMapping(path = "/edit")
    public ModelAndView edit(ModelAndView modelAndView) {
        modelAndView.setViewName("editProfile");
        modelAndView.addObject("editUser", new UserDto());
        return modelAndView;
    }

    @PostMapping(path = "/submitEdit")
    public ModelAndView userProfile(@Valid @ModelAttribute("editUser") UserDto userDto, BindingResult bindingResult, ModelAndView modelAndView, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("editProfile");
        } else {
            User loggedUser = (User) httpSession.getAttribute("loggedUser");
            ifNotEdited(userDto, loggedUser);
            httpSession.setAttribute("loggedUser", loggedUser);
            modelAndView.setViewName("/profile");
        }
        return modelAndView;
    }
  
    @GetMapping(path = "/myItems")
    public ModelAndView showMyItems(ModelAndView modelAndView, HttpSession httpSession) {
        modelAndView.setViewName("myItem");
        return modelAndView;

    private void ifNotEdited(UserDto userDto, User loggedUser) {
        if (userDto.getName().equals("")){
            System.out.println(userDto.getName().equals(""));
        }else{
            loggedUser.setName(userDto.getName());
            userService.updateName(loggedUser.getId(), loggedUser.getName());
        }
        if (userDto.getSurname().equals("")){
            System.out.println(userDto.getSurname().equals(""));
        }else{
            loggedUser.setSurname(userDto.getSurname());
            userService.updateSurname(loggedUser.getId(), loggedUser.getSurname());
        }
        if (userDto.getPassword().equals("")){
            System.out.println(userDto.getPassword().equals(""));
        }else{
            loggedUser.setPassword(userDto.getPassword());
            userService.updatePassword(loggedUser.getId(), loggedUser.getPassword());
        }
    }

}
