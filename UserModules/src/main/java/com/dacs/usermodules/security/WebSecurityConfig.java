package com.dacs.usermodules.security;

import com.dacs.usermodules.security.oauth2.CustomerOAuth2UserService;
import com.dacs.usermodules.security.oauth2.OauthLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomerOAuth2UserService customerOAuth2UserService;
    private final OauthLoginSuccessHandler successHandler;
    private final LoginDefaultSuccessHandler defaultSuccessHandler;

    public WebSecurityConfig(CustomerOAuth2UserService customerOAuth2UserService, OauthLoginSuccessHandler successHandler, LoginDefaultSuccessHandler defaultSuccessHandler) {
        this.customerOAuth2UserService = customerOAuth2UserService;
        this.successHandler = successHandler;
        this.defaultSuccessHandler = defaultSuccessHandler;
    }

    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomerDetailsService();
    }

    private DaoAuthenticationProvider provider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/account/**", "/carts/**", "/addresses/**").authenticated()
                .antMatchers("/orders/**").authenticated()
                .antMatchers("/api/addresses/**", "/api/carts/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(customerOAuth2UserService)
                .and()
                .successHandler(successHandler)
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .successHandler(defaultSuccessHandler)
                    .permitAll()
                .and().logout().permitAll()
                .and().rememberMe().key("secretKey")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/common/**");
    }
}
