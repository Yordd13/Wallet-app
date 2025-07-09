package app.web;

import app.security.AuthenticationDetails;
import app.user.model.User;
import app.user.service.UserService;
import app.web.dto.NewRegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
@RequestMapping("/")
public class IndexController {

    private final UserService userService;

    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getBasePage(){
        return "index";
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage(){
        ModelAndView modelAndView = new ModelAndView("register");

        modelAndView.addObject("newRegistrationRequest", new NewRegistrationRequest());

        return modelAndView;
    }

    @PostMapping("/register")
    public String postRegisterPage(@Valid NewRegistrationRequest newRegistrationRequest, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "register";
        }

        userService.register(newRegistrationRequest);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(@RequestParam(value = "error", required = false) String error){
        ModelAndView modelAndView = new ModelAndView("login");

        if(error != null) {
            modelAndView.addObject("errorMessage", "Incorrect email or password!");
        }

        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(@AuthenticationPrincipal AuthenticationDetails authenticationDetails){
        ModelAndView modelAndView = new ModelAndView("home");

        User user = userService.getUserById(authenticationDetails.getUserId());
        BigDecimal balance = user.getBalance();

        modelAndView.addObject("balance", balance);

        return modelAndView;
    }
}
