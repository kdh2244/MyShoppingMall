package com.example.myshopping.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AuthController {

    @GetMapping("/auth/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String accessCode,
                                        HttpServletResponse httpServletResponse){

    }
}
