package com.aniketmore.springsecjwt.controller;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class IndexController {
    private Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    UserDetailsService userdetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${app.jwt.secret}")
    String secret;

    @PostMapping("/auth/login")
    public LoginRespDto postMethodName(@RequestBody LoginReqDto loginReq) throws Exception {
        try {
            var user = userdetailsService.loadUserByUsername(loginReq.getUsername());
            var res = passwordEncoder.matches(loginReq.getPassword(), user.getPassword());
            if (!res) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid password");
            }
            // create jwt
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String jwts = JWT.create()
                    .withSubject(user.getUsername())
                    .withArrayClaim("roles",
                            user.getAuthorities().stream().map((a) -> a.getAuthority()).toArray(String[]::new))
                    .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
                    .withIssuedAt(new Date()).sign(algorithm);
            return new LoginRespDto(jwts);
        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid  username");
        }
    }

    @GetMapping("/hello")
    public String handleHello(Authentication auth) {
        return "hello";
    }

    @GetMapping("/user/hello")
    public String handleUserHello(Authentication auth) {
        return "hello user: " + (String) auth.getPrincipal();
    }

    @GetMapping("/admin/hello")
    public String handleAdminHello(Authentication auth) {
        return "hello admin: " + (String) auth.getPrincipal();
    }

}

@Data
class LoginReqDto {
    private String username;
    private String password;
}

@Data
@AllArgsConstructor
class LoginRespDto {
    private String accessToken;
}