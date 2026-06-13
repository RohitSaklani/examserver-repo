package com.exam.config;


import com.exam.model.AdminDetail;
import com.exam.model.Role;
import com.exam.model.Users;
import com.exam.repository.UserRepository;
import com.exam.util.CustomAuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebSecurityConfiguration {


    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;
    @Autowired
    private final CustomAuthEntryPoint customAuthEntryPoint;

    @Value("${app.origins}")
    private String origins;

    public WebSecurityConfiguration(JwtFilter jwtFilter, CustomAuthEntryPoint customAuthEntryPoint, UserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.customAuthEntryPoint = customAuthEntryPoint;
        this.userDetailsService = userDetailsService;

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("encoded password : "+ passwordEncoder().encode("chaman123"));

        http.cors(cors -> {
                })
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll()
                        .dispatcherTypeMatchers(jakarta.servlet.DispatcherType.FORWARD, jakarta.servlet.DispatcherType.ERROR).permitAll()

                        .requestMatchers("/api/auth/**", "/api/user/register", "/api/user/quiz/*/questions", "/api/user/quiz/*", "/api/user/quiz-data", "/api/user/quiz", "/api/user/subject", "/api/user/filter", "/api/user/quiz/filter").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthenticationProvider())
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint(customAuthEntryPoint)
                                .accessDeniedHandler(customAuthEntryPoint))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
List<String > originsList = Arrays.asList(origins.split(","));
        config.setAllowedOrigins(originsList);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//
//    @Bean
//    CommandLineRunner createAdminUser(
//            UserRepository userRepository,
//            PasswordEncoder passwordEncoder) {
//
//        return args -> {
//
//            String username = "admin";
//
//            if (userRepository.findByUsername(username).isEmpty()) {
//
//                Users admin = new Users();
//                admin.setUsername(username);
//                admin.setPassword(passwordEncoder.encode("chaman123"));
//           //     admin.setEnabled(true);
//                admin.setRole(Role.ADMIN);
//
//
////
////                AdminDetail adminDetail =  new AdminDetail();
////                adminDetail.setEmail("rohit1510@gmail.com");
////                adminDetail.setFirstName("rohitadmin");
////                adminDetail.setLastName("saklani");
////                adminDetail.setPhone("1234567890");
////                adminDetail.setUser(admin);
//
//
//                userRepository.save(admin);
//
//                System.out.println("Admin user created.");
//            }
//        };
//    }



}
