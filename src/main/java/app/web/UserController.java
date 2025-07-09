package app.web;

import app.security.AuthenticationDetails;
import app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/wallet/add")
    public String addMonetToWallet(@AuthenticationPrincipal AuthenticationDetails authenticationDetails){

        UUID userId = authenticationDetails.getUserId();
        userService.addMoneyToWallet(userId, BigDecimal.TEN);

        return "redirect:/home";
    }
}
