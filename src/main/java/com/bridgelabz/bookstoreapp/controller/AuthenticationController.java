package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.RabbitMqDto;
import com.bridgelabz.bookstoreapp.entity.Role;
import com.bridgelabz.bookstoreapp.entity.User;
import com.bridgelabz.bookstoreapp.model.ERole;
import com.bridgelabz.bookstoreapp.payload.request.LoginRequest;
import com.bridgelabz.bookstoreapp.payload.request.SignupRequest;
import com.bridgelabz.bookstoreapp.payload.response.JwtResponse;
import com.bridgelabz.bookstoreapp.payload.response.MessageResponse;
import com.bridgelabz.bookstoreapp.repository.RoleRepository;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.service.UserDetailsImpl;
import com.bridgelabz.bookstoreapp.utility.JwtUtils;
import com.bridgelabz.bookstoreapp.utility.RabbitMq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RabbitMqDto rabbitMqDto;

    @Autowired
    private RabbitMq rabbitMq;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if (user.get().isVerified()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));
        } else {
            return new ResponseEntity("Please verify your account by visiting your email account to proceed!!", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        sendEmailToVerify(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private void sendEmailToVerify(User user) throws MailException {
        rabbitMqDto.setTo(user.getEmail());
        rabbitMqDto.setFrom("rajkush211.rk@gmail.com");
        rabbitMqDto.setSubject("Welcome to Book Store, Thank you for registering with us!!");
        rabbitMqDto.setBody("Please click this link to verify your account " + "http://localhost:8080/verifyaccount/" + user.getId());
        rabbitMq.sendMessageToQueue(rabbitMqDto);
    }
}