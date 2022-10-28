package ua.com.foreach.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ua.com.foreach.jwtutils.google.GoogleTokenValidationService;
import ua.com.foreach.jwtutils.JwtUserDetailsService;
import ua.com.foreach.jwtutils.TokenManager;
import ua.com.foreach.models.JwtRequestModel;
import ua.com.foreach.models.JwtResponseModel;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@CrossOrigin
@AllArgsConstructor
public class LoginJwtController {

    private GoogleTokenValidationService googleTokenValidationService;
    private JwtUserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JwtRequestModel request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwtToken = tokenManager.generateJwtToken(userDetails);
        return new ResponseEntity<>(new JwtResponseModel(jwtToken), HttpStatus.OK);
    }

    @PostMapping("/login/google")
    public ResponseEntity<?> createTokenByGoogle(HttpServletRequest request) throws Exception {
        try {
            final UserDetails userDetails = googleTokenValidationService.validateGoogleUser(request.getParameter("credential"));
            final String jwtToken = tokenManager.generateJwtToken(userDetails);
            return new ResponseEntity<>(new JwtResponseModel(jwtToken), HttpStatus.OK);
        } catch (IOException | GeneralSecurityException e) {
            throw new Exception("Invalid Google token");
        }
    }

    @GetMapping("/getClaims")  //todo: for testing User
    public String getClaims(@RequestHeader("Authorization") String token) {
        return tokenManager.getClaimsFromToken(token.substring(7));
    }
}

