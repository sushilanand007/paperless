package com.coviam.codiecon.paperless;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Sushil on 23/06/18.
 */
@SpringBootApplication
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/", "/admin.html")
            .permitAll()
            .anyRequest()
            .authenticated();
    }
//
//    @Bean
//    @ConfigurationProperties("google.client")
//    public AuthorizationCodeResourceDetails google() {
//        return new AuthorizationCodeResourceDetails();
//    }
//
//    @Bean
//    @ConfigurationProperties("google.resource")
//    public ResourceServerProperties googleResource() {
//        return new ResourceServerProperties();
//    }

}

