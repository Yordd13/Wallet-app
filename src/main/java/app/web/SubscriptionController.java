package app.web;

import app.security.AuthenticationDetails;
import app.user.model.User;
import app.user.model.UserRole;
import app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final UserService userService;

    @Autowired
    public SubscriptionController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getSubscriptionsPage(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        ModelAndView modelAndView = new ModelAndView();

        User user = userService.getUserById(authenticationDetails.getUserId());

        modelAndView.addObject("user", user);

        return modelAndView;
    }
}
