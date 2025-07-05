package app.web;

import app.exception.RegistrationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(RegistrationException.class)
    public String handleRegistrationException(final RegistrationException e, RedirectAttributes redirectAttributes) {

        for(String errorMessage : e.getErrorMessages()){
            if (errorMessage.contains("Username")) {
                redirectAttributes.addFlashAttribute("usernameExistsExceptionMessage", errorMessage);
            } else if (errorMessage.contains("Email")) {
                redirectAttributes.addFlashAttribute("emailExistsExceptionMessage", errorMessage);
            }
        }

        return "redirect:/register";
    }
}
