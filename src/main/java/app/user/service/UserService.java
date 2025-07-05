package app.user.service;

import app.exception.DomainException;
import app.exception.RegistrationException;
import app.security.AuthenticationDetails;
import app.user.model.UserRole;
import app.user.repository.UserRepository;
import app.web.dto.NewRegistrationRequest;
import jakarta.transaction.Transactional;
import app.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(NewRegistrationRequest newRegistrationRequest) {
        List<String> errorMessages = new ArrayList<>();

        // Check if username is taken
        if (userRepository.findByUsername(newRegistrationRequest.getUsername()).isPresent()) {
            errorMessages.add("Username \"" + newRegistrationRequest.getUsername() + "\" is already taken.");
        }

        // Check if email is taken
        if (userRepository.findByEmail(newRegistrationRequest.getEmail()).isPresent()) {
            errorMessages.add("Email \"" + newRegistrationRequest.getEmail() + "\" is already taken.");
        }

        if(!errorMessages.isEmpty()) {
            throw new RegistrationException(errorMessages);
        }

        User user = User.builder()
                .username(newRegistrationRequest.getUsername())
                .email(newRegistrationRequest.getEmail())
                .password(passwordEncoder.encode(newRegistrationRequest.getPassword()))
                .isActive(true)
                .role(UserRole.USER)
                .profilePictureUrl("https://cdn.pixabay.com/photo/2018/11/13/21/43/avatar-3814049_1280.png")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        log.info("User [{}] successfully created.", user.getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DomainException("User with this username does not exist."));

        return new AuthenticationDetails(user.getId(),username,user.getPassword(),user.getEmail(),user.getRole(),user.isActive());
    }
}
