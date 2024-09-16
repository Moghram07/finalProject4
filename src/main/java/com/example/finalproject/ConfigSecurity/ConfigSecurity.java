package com.example.finalproject.ConfigSecurity;

import com.example.finalproject.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disable CSRF for testing purposes (not for production!)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                        // Document permissions
                        .requestMatchers("/api/v1/document/**").hasAnyAuthority("TUTOR", "STUDENT")

                        // Session permissions
                        .requestMatchers("/api/v1/session/add/**", "/api/v1/session/update/**", "/api/v1/session/delete/**", "/api/v1/session/assign/tutor/**", "/api/v1/session/assign/student/**", "/api/v1/session/start/**", "/api/v1/session/cancel/**", "/api/v1/session/end/**", "/api/v1/session/block/**")
                        .hasAuthority("TUTOR") // Only tutors can manage sessions
                        .requestMatchers("/api/v1/session/get", "/api/v1/session/students/**", "/api/v1/session/max-participants/**")
                        .hasAnyAuthority("TUTOR", "STUDENT") // Students can view session details

                        // Face-to-Face permissions
                        .requestMatchers("/api/v1/facetoface/**").hasAuthority("TUTOR") // Only tutors can manage Face-to-Face meetings

                        // Video permissions
                        .requestMatchers("/api/v1/video/add/**", "/api/v1/video/update/**", "/api/v1/video/delete/**", "/api/v1/video/increase-price/**", "/api/v1/video/decrease-price/**", "/api/v1/video/delete-by-course/**")
                        .hasAuthority("TUTOR") // Only tutors can manage videos
                        .requestMatchers("/api/v1/video/get/**", "/api/v1/video/price-range/**", "/api/v1/video/search/**")
                        .hasAnyAuthority("TUTOR", "STUDENT") // Both tutors and students can view and search videos

                        // Zoom permissions
                        .requestMatchers("/api/v1/zoom/add/**", "/api/v1/zoom/update/**", "/api/v1/zoom/delete/**", "/api/v1/zoom/assign/**")
                        .hasAuthority("TUTOR") // Only tutors can manage Zoom meetings
                        .requestMatchers("/api/v1/zoom/get/**").hasAnyAuthority("TUTOR", "STUDENT") // Both tutors and students can view Zoom meetings

                        // Any other requests
                        .anyRequest().authenticated() // Ensure all other requests require authentication
                        .and()
                        .logout().logoutUrl("/api/v1/auth/logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .and()
                        .httpBasic();

        return http.build();
    }
}