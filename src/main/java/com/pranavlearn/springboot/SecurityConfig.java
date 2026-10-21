package com.pranavlearn.springboot;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
//import com.pranavlearn.springboot.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
//        DaoAuthenticationProvider provider=new DaoAuthenticationProvider(userService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(DaoAuthenticationProvider provider) {
//        return new ProviderManager(provider);
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, DaoAuthenticationProvider provider) throws Exception {
//        httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll().anyRequest().authenticated()).httpBasic(Customizer.withDefaults()).authenticationProvider(provider);
//        return httpSecurity.build();
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN").requestMatchers(HttpMethod.POST, "/software-engineers/post-engineer").hasRole("ADMIN").requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll().anyRequest().authenticated()).sessionManagement(s-> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).oauth2ResourceServer(oauth->oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
//        return httpSecurity.build();
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN").requestMatchers(HttpMethod.POST, "/software-engineers/post-engineer").hasRole("ADMIN")).sessionManagement(s-> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).oauth2ResourceServer(oauth->oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
//        return httpSecurity.build();
//    }
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter=new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> realmAccess=jwt.getClaimAsMap("realm_access");
            if (realmAccess==null) return List.of();
            List<String> roles=(List<String>) realmAccess.get("roles");
            if (roles == null) return List.of();
            return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toList());
        });
        return converter;
    }
//    @Bean
//    @Order(1)
//    SecurityFilterChain apiChain(HttpSecurity http) throws Exception {
//        http.securityMatcher("/software-engineers/**").csrf(csrf->csrf.disable())
//                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.GET, "/software-engineers/**").authenticated()
//                        .requestMatchers(HttpMethod.POST, "/software-engineers/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/software-engineers/**").hasRole("ADMIN")
//                        .anyRequest().denyAll()).oauth2ResourceServer(o->o.jwt(j->j.jwtAuthenticationConverter(jwtAuthenticationConverter())));
//        return http.build();
//    }


    @Bean
    @Order(1)
    SecurityFilterChain apiChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/software-engineers/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/software-engineers/**")
                        .authenticated()
                        .requestMatchers(HttpMethod.POST, "/software-engineers/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/software-engineers/**")
                        .hasRole("ADMIN")
                        .anyRequest().denyAll()
                );

        return http.build();
    }


    @Bean
    @Order(2)
    SecurityFilterChain loginChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(a->a.anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

    //@Bean
//    public CommandLineRunner seedUser(
//            UserRepository userRepository,
//            PasswordEncoder passwordEncoder) {
//        return args -> {
//            if (userRepository.findByUsername("abc").isEmpty()) {
//                userRepository.save(
//                        new User(
//                                "abc",
//                                passwordEncoder.encode("pass"),
//                                "ROLE_ADMIN"
//                        )
//                );
//            }
//        };
//    }
}
