package com.doctoranywhere.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeHttpRequests((authorize) -> {
            authorize.anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

// @Bean
// 	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
// 		http
// 			.authorizeHttpRequests((requests) -> requests
// 				.requestMatchers("/v1/**").permitAll()
// 				.anyRequest().authenticated()
// 			)
// 			.formLogin((form) -> form
// 				.permitAll()
// 			)
// 			.logout((logout) -> logout.permitAll());

// 		return http.build();
// 	}

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public UserDetailsService userDetailsService() {
//         UserDetails user = User
//             .withUsername("user")
//             .password(passwordEncoder().encode("your_custom_password_here"))
//             .roles("USER")
//             .build();

//         return new InMemoryUserDetailsManager(user);
// }
// }

