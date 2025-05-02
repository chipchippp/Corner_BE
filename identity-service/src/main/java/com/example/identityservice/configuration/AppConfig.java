package com.example.identityservice.configuration;

import com.example.identityservice.service.impl.UserService;
import lombok.AccessLevel;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppConfig {
    UserService userService;
//    PreFilter filter;
    String[] WHITE_LIST = {"/api/v1/auth/**"};

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Bean
    public SecurityFilterChain configure(@NonNull HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Vô hiệu hóa CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST).permitAll() // Các API `/api/v1/auth/**` không cần xác thực
                        .anyRequest().authenticated()) // Các request khác cần xác thực
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(jwtDecoder()))); // Cấu hình JWT decoder nếu cần
//                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS)) // Không sử dụng session (JWT)
//                .authenticationProvider(provider()) // Cung cấp provider để xử lý xác thực
//                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); // Chạy PreFilter trước bộ lọc xác thực
        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

//    @Bean
//    public AuthenticationProvider provider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
////        provider.setUserDetailsService(userService.getUserDetailsService());
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
}
