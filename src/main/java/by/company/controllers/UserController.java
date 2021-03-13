package by.company.controllers;

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
    private UserService userService;

    @GetMapping
    public ModelAndView getProfilePage(ModelAndView modelAndView, HttpSession session) {
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    /*    @PostMapping(path = "/logout")
        public ModelAndView logout(ModelAndView modelAndView, HttpSession session){
            session.invalidate();
            modelAndView.setViewName("redirect:/authorize");
            return modelAndView;
        }
    */
    @GetMapping(path = "/logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession httpSession) {
        httpSession.invalidate();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @PostMapping(path = "/edit")
    public ModelAndView editProfile(ModelAndView modelAndView, HttpSession httpSession) {
        User editUser = (User) httpSession.getAttribute("loggedUser");
        modelAndView.addObject("editUser", editUser);
        modelAndView.setViewName("editProfile");
        return modelAndView;
    }

    @PostMapping(path = "/save")
    public ModelAndView userProfile(@Valid @ModelAttribute("editUser") User user, BindingResult bindingResult, ModelAndView modelAndView, HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("editProfile");
        } else {

            User newUser = (User) httpSession.getAttribute("loggedUser");

            userService.updatePassword(newUser.getId(), user.getPassword()).getDescription();
           userService.updateName(newUser.getId(), user.getName()).getDescription();
           userService.updateSurname(newUser.getId(), user.getSurname()).getDescription() ;

            user.setId(newUser.getId());

            httpSession.setAttribute("loggedUser", user);
            modelAndView.addObject("editUser", user);

            modelAndView.setViewName("profile");
        }

        return modelAndView;
    }

    @GetMapping(path = "/myItems")
    public ModelAndView showMyItems(ModelAndView modelAndView, HttpSession httpSession) {
        modelAndView.setViewName("myItem");
        return modelAndView;
    }

}
