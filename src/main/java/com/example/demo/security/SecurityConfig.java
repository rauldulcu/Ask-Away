package com.example.demo.security;

import com.example.demo.util.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/users").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/users/byEmail").permitAll()
                .antMatchers(HttpMethod.GET, "/users/byUsername").permitAll()
                .antMatchers(HttpMethod.POST, "/posts").permitAll()
                .antMatchers(HttpMethod.GET, "/posts").permitAll()
                .antMatchers(HttpMethod.DELETE, "/posts/{id}").hasAnyAuthority(Role.ADMIN.toString(),Role.MODERATOR.toString())
                .antMatchers(HttpMethod.DELETE, "/comments/{id}").hasAnyAuthority(Role.ADMIN.toString(),Role.MODERATOR.toString())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout()
                .permitAll();
    }

}
