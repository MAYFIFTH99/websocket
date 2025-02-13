package fastcampus.websocketchat.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/swagger-ui/**", "/swagger-ui.html",
                                        "/v3/api-docs/**").permitAll()
                                .anyRequest().authenticated())

                .oauth2Login(withDefaults())
                .csrf(csrf -> csrf.disable());

        return httpSecurity.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain consultantSecurityFilterChain(HttpSecurity httpSecurity)
            throws Exception {
        httpSecurity
                .securityMatcher("/consultants/**", "consultants","/login")
                .authorizeHttpRequests(request ->
                        request.requestMatchers(HttpMethod.POST, "/consultants").permitAll()
                                .anyRequest().hasRole("CONSULTANT"))
                .formLogin(withDefaults())
                .csrf(csrf -> csrf.disable());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
