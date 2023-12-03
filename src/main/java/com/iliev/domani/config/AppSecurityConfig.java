package com.iliev.domani.config;

import com.iliev.domani.repository.UserRepository;
import com.iliev.domani.service.DomaniUserDetailService;
import com.iliev.domani.service.OAuthSuccessHandler;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class AppSecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, OAuthSuccessHandler oAuthSuccessHandler) throws Exception {
       // http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers("/","/menu","/booking","/booking-friday","/specialties","/error/**").permitAll()
                    .requestMatchers("/user/login","/user/register").anonymous()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated();
        });

        http.formLogin(login -> {
            login.loginPage("/user/login").permitAll();
            login.usernameParameter("email");
            login.passwordParameter("password");
            login.successForwardUrl("/user/login-success").permitAll()
                    .failureUrl("/user/login-failed");
        });
        http.oauth2Login(oauth -> {
            oauth.loginPage("/user/login")
                    .successHandler(oAuthSuccessHandler);
        });
        http.logout(logout -> {
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true);
        });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new DomaniUserDetailService(userRepository);
    }

}
