package com.coviam.codiecon.paperless;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Sushil on 23/06/18.
 */
@SpringBootApplication
@EnableOAuth2Sso
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/", "/admin.html","/oauth/authorize", "/oauth/confirm_access")
            .permitAll()
            .anyRequest()
            .authenticated();
    }
}

