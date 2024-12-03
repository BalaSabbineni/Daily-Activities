package com.personal.dailyActivities.auth.controller;

import com.personal.dailyActivities.auth.dto.LoginDto;
import com.personal.dailyActivities.auth.entity.UserRegister;
import com.personal.dailyActivities.auth.jwt.JwtService;
import com.personal.dailyActivities.auth.repository.UserRegisterRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class UserAuthController {

    private final UserRegisterRepository userRegisterRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserAuthController(UserRegisterRepository userRegisterRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRegisterRepository = userRegisterRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /*
     * This API registers user and encodes user password.
     */
    @PostMapping("/register")
    public UserRegister register(@RequestBody UserRegister userDetails) {
        userDetails.setPassWord(bCryptPasswordEncoder.encode(userDetails.getPassWord()));

        return userRegisterRepository.save(userDetails);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDetails) {
        var authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginDetails.getUserName(), loginDetails.getPassWord()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(loginDetails);
        }
        return "Wrong credentials";
    }
}
