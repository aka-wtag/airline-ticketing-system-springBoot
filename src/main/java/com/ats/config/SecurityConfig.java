package com.ats.config;

import com.ats.config.jwt.JwtAuthenticationEntryPoint;
import com.ats.config.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig extends GlobalMethodSecurityConfiguration {
    private final JwtAuthenticationEntryPoint point;
    private final JwtAuthenticationFilter filter;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint point, JwtAuthenticationFilter filter) {
        this.point = point;
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/users/login").permitAll()
                .antMatchers(HttpMethod.POST, "/passengers").permitAll()
                .antMatchers(HttpMethod.PUT, "/passengers/*").hasAnyRole("Admin", "Passenger")
                .antMatchers(HttpMethod.DELETE, "/passengers/*").hasAnyRole("Admin", "Passenger")
                .antMatchers(HttpMethod.GET, "/passengers").hasRole("Admin")
                .antMatchers(HttpMethod.GET, "/passengers/*").hasAnyRole("Admin", "Passenger")
                .antMatchers("/airlines").hasRole("Admin")
                .antMatchers("/airlines/*").hasRole("Admin")
                .antMatchers(HttpMethod.POST,"/flights").hasRole("Admin")
                .antMatchers(HttpMethod.PUT,"/flights/*").hasRole("Admin")
                .antMatchers(HttpMethod.DELETE,"/flights/*").hasRole("Admin")
                .antMatchers(HttpMethod.GET, "/flights").permitAll()
                .antMatchers(HttpMethod.GET, "/flights/*").permitAll()
                .antMatchers(HttpMethod.POST, "/passengers/*/bookings").hasAnyRole("Admin", "Passenger")
                .antMatchers(HttpMethod.GET, "/passengers/*/bookings/*").hasAnyRole("Admin", "Passenger")
                .antMatchers(HttpMethod.GET, "/bookings").hasRole("Admin")
                .antMatchers(HttpMethod.DELETE, "/passengers/*/bookings/*").hasAnyRole("Admin", "Passenger")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable();
        http.formLogin().disable();
        http.httpBasic().disable();

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
