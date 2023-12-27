package com.example.securityTest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        try {
            httpSecurity.csrf().disable()//(크로스 사이트 요청 변조 공격) 방어 설정 끄기, 프론트엔드 막을거다.
                    .authorizeRequests() //인증받은 요청을 받겠다.
                    .antMatchers("/login","/member/login","/member/signup").permitAll() //이거 허락하고
                    .antMatchers("/member/mypage").hasRole("USER") //이거 허락하고
                    .anyRequest().authenticated();//나머지 요청 거부하겠음.
            httpSecurity.formLogin() //formLogin : 로그인 처리하는 controller 느낌
                    //.loginPage() //로그인 페이지 설정을 할 수 있는 매소드
                    //.loginProcessingUrl("/member/login") // 로그인 처리하는 곳
                    //.defaultSuccessUrl("/member/mypage"); //로그인 성공 후 이동하는 곳
                    .disable(); // 폼 로그인 끄기
            return httpSecurity.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
