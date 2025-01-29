package spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // by configuring the HttpSecurity object we are indirectly configuring the authorisation filter

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/greeting").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/secret").authenticated();

        // hasRole assumes that our granted authorities are prefixed with ROLE_
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/admin").hasAuthority("admin");

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/message").authenticated();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/users").permitAll();

        /*
         * CSRF: Cross Site Request Forgery
         * CSRF is an attack where the hacker tricks the user into making a request, e.g. to his/her bank
         * Assuming the user has recently logged then a session cookie will be passed to the server logging the user in
         * The malicious request effects some undesirable change on the database
         *
         * Spring Security automatically prevents the accessing of POST, PUT, & DELETE requests from other domains
         * Spring Security applies CSRF protection by default
         *
         * Q. Do we have to worry about CSRF attacks in this case?
         * A. No. Because this app is stateless - it does not use sessions or cookies.
         */

        // turn on basic authentication; expect creds in the Auth header
        httpSecurity.httpBasic();

        // no sessions; no cookies
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // turn off CSRF protection (only do this if the app is stateless; make sure you GET requests are idempotent)
        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
