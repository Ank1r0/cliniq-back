package com.example.cliniqserv.congif;

import com.example.cliniqserv.extra.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.example.cliniqserv.services.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final  JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserService userService;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            CorsConfigurer<HttpSecurity> apply = http.cors(withDefaults()).csrf(AbstractHttpConfigurer::disable)
                    // .authorizeHttpRequests(request -> request.requestMatchers("/api/auth/**")
                    .authorizeHttpRequests(request -> request.requestMatchers("/api/**")
                            .permitAll().requestMatchers("/api/hello")
                            .permitAll().requestMatchers("/api/auth/signin")
                            .permitAll().requestMatchers("/api/auth/signup")
//                           .permitAll().requestMatchers("/api/user/getAllUsers")
                            .permitAll()
                            .requestMatchers("/api/user/getAllUsers").hasAuthority(Role.Admin.name())
                            .requestMatchers("/api/user/getUserByLogin/").hasAuthority(Role.Patient.name())
//                          .requestMatchers("/api/admin").hasAuthority(Role.Admin.name())
                            .requestMatchers("/api/admin").hasAuthority(Role.Admin.name())
                            .requestMatchers("/api/user/updateUserById/").hasAuthority(Role.Admin.name())
                            .requestMatchers("/api/user/updateUserById/").hasAuthority(Role.Patient.name())
                            .requestMatchers("/api/user/updateUserWithAppoById/").hasAuthority(Role.Admin.name())
                            //deleteUserById/{id}
                            .requestMatchers("/api/deleteUserById/").hasAuthority(Role.Admin.name())
                            .requestMatchers("/api/medicalRecord/").hasAuthority(Role.Admin.name())
                            .requestMatchers("/api/medicalRecord/updateMedRecord/").hasAuthority(Role.Admin.name())

//                          .requestMatchers("/api/user").hasAuthority(Role.Patient.name())
                            .anyRequest().authenticated())
                    .httpBasic(withDefaults())
                    .formLogin(withDefaults())
                    .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authenticationProvider(authenticationProvider()).addFilterBefore(
                            jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                    ).apply(corsConfigurer());

            return http.build();
    }
    private CorsConfigurer<HttpSecurity> corsConfigurer() {
        return new CorsConfigurer<HttpSecurity>()
                .configurationSource(request -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.addAllowedOrigin("http://localhost:3000");
                    corsConfig.addAllowedHeader("*");
                    corsConfig.addAllowedMethod("*");
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                });
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}