package com.example.securityTest.member.controller;

import com.example.securityTest.member.model.Member;
import com.example.securityTest.member.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
@RestController
@RequestMapping("/member")
public class Membercontroller {
    private MemberService memberService;
    private  AuthenticationManager authenticationManager;

    public Membercontroller(MemberService memberService, AuthenticationManager authenticationManager) {
        this.memberService = memberService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity signup (String username, String password) {
        memberService.signup(username, password);
        return ResponseEntity.ok().body("ok");
    }


    @RequestMapping(method = RequestMethod.GET, value = "/mypage")
    public ResponseEntity myPage () {
       // memberService.signup(username, password);
        return ResponseEntity.ok().body("My page");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity login(String username, String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok().body(((Member)authentication.getPrincipal()).getId());
    }

}
