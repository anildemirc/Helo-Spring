package tr.anil.questapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tr.anil.questapp.entity.RefreshToken;
import tr.anil.questapp.entity.User;
import tr.anil.questapp.exception.UserNotFoundException;
import tr.anil.questapp.request.RefreshRequest;
import tr.anil.questapp.request.UserRequest;
import tr.anil.questapp.response.AuthResponse;
import tr.anil.questapp.security.JwtTokenProvider;
import tr.anil.questapp.service.RefreshTokenService;
import tr.anil.questapp.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserRequest loginRequest) {
        AuthResponse authResponse = new AuthResponse();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = userService.getUserByUsername(loginRequest.getUsername());
        if (user == null) {
            authResponse.setMessage("User not found");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
        authResponse.setAccessToken("Bearer "+jwtTokenProvider.generateJwtToken(auth));
        RefreshToken refreshToken = refreshTokenService.getByUser(user.getId());
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());
        authResponse.setMessage("User successfully logined");
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest registerRequest) {
        AuthResponse authResponse = new AuthResponse();
        if (userService.getUserByUsername(registerRequest.getUsername()) != null) {
            authResponse.setMessage("Username already in use");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setAvatar(7);
        user = userService.saveUser(user);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(registerRequest.getUsername(), registerRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        authResponse.setAccessToken("Bearer "+jwtTokenProvider.generateJwtToken(auth));
        authResponse.setMessage("User successfully registered");
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request) {
        AuthResponse response = new AuthResponse();
        RefreshToken refreshToken = refreshTokenService.getByUser(request.getUserId());
        if (refreshToken.getToken().equals(refreshToken.getToken()) && refreshTokenService.isRefreshExpired(refreshToken)) {
            User user = userService.getUser(request.getUserId());
            if (user == null)
                throw new UserNotFoundException();
            response.setAccessToken("Bearer "+jwtTokenProvider.generateJwtTokenByUsername(user.getId()));
            response.setUserId(user.getId());
            response.setMessage("Refreshing success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setMessage("refresh token is not valid");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFound() {

    }
}