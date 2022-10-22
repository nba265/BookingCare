/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.doctorcare.api.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.swing.text.html.parser.Parser;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

/*    @Bean
    public AuthenticationTokenFilter authenticationJwtTokenFilter() {
        return new AuthenticationTokenFilter();
    }*/

/*    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().antMatchers("/resources/templates/**")
                .antMatchers("/resources/maxmind/**")
                .antMatchers("/api-docs/**", "/swagger-ui-custom.html", "/swagger-ui/**");
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

//    @Bean
//    public void authenticationManagerBuilder(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
//    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.headers()
//                .contentSecurityPolicy("script-src 'self' https://domain.example.com; " +
//                        "object-src https://domain.example.com; report-uri /csp-report-endpoint/");

        // enable cors and prevent CSRF
//        http = http.cors().and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and();
        http = http.cors().and().csrf().disable();
        // set session management stateless
        http = http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        // set unauthorized request exception handler
        http = http.exceptionHandling()
                .authenticationEntryPoint(this.unauthorizedEntryPoint).and();

        // set permission on endpoints
        http.authorizeRequests().antMatchers("/**", "/login", "/logout", "/home").permitAll();
        http.authorizeRequests().antMatchers("/user/*").access("hasAnyRole('ROLE_ADMIN,ROLE_MANAGER')")
                .antMatchers("/manager/*").access("hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/*").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/user/*").access("hasRole('ROLE_USER')")
                .anyRequest().authenticated().and()
                .logout().invalidateHttpSession(true).deleteCookies("JSESSIONID");

        // Add jwt token filter to validate tokens with every request

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setExposedHeaders(List.of("Authorization", ""));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

/*    @Bean
    public Parser uaParser() throws IOException {
        return new Parser();
    }*/

 /*   @Bean(name="GeoIPCity")
    public DatabaseReader databaseReader() throws IOException {
        File database = ResourceUtils.getFile("classpath:maxmind/GeoLite2-City.mmdb");
        return new DatabaseReader.Builder(database).build();
    }*/

}
