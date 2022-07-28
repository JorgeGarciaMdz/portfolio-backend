package com.jorge.backend.portfolio.auth.controller;

import javax.validation.Valid;

import com.jorge.backend.portfolio.auth.dto.AuthenticationRequest;
import com.jorge.backend.portfolio.auth.dto.AuthenticationResponse;
import com.jorge.backend.portfolio.auth.dto.UserDTO;
import com.jorge.backend.portfolio.auth.service.JwtUtils;
import com.jorge.backend.portfolio.auth.service.UserDetailsCustomService;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class UserAuthController {
    private UserDetailsCustomService userDetailsCustomService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    @Autowired
    public UserAuthController(
            UserDetailsCustomService userDetailsCustomService,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils) {
        this.userDetailsCustomService = userDetailsCustomService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/singup")
    public <T> ResponseEntity<?> singUp(@Valid @RequestBody UserDTO userDTO) throws Exception {
        try {
            this.userDetailsCustomService.save(userDTO);
        } catch (DataIntegrityViolationException e) {
            String message = "username: " + userDTO.getUsername() + " is already existent";
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
        } catch (ConstraintViolationException e) {
            String message = "username: " + userDTO.getUsername() + " is already existent";
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/singin")
    public <T> ResponseEntity<?> singIn(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        UserDetails userDetails;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername().toString(),
                            authenticationRequest.getPassword().toString()));
            userDetails = (UserDetails) auth.getPrincipal();
        } catch (BadCredentialsException e) {
            System.out.println("bad credential");
            throw new Exception("Incorrect username or password", e);
        }

        final String jwt = jwtUtils.generateToken(userDetails);
        // final String jwt = "ads";

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
