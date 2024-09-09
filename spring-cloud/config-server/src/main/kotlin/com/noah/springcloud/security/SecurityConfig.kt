package com.noah.springcloud.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun user(): InMemoryUserDetailsManager {
        val user1 =
            User.withUsername("user1")
                .password("{noop}1234")
                .roles("USER")
                .build()
        val user2 =
            User.withUsername("user2")
                .password("{noop}1234")
                .roles("ADMIN")
                .build()

        return InMemoryUserDetailsManager(
            user1, user2
        )
    }

    @Bean
    fun configure(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring().requestMatchers("/ignore")
        }
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf ->
                csrf.disable()
            }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/ignore").permitAll()  // /ignore는 인증 필요 없음
                    .requestMatchers("/permit-all").permitAll()  // /permit-all은 인증 필요 없음
                    .requestMatchers("/user").hasRole("USER")  // /user는 ROLE_USER만 접근 가능
                    .requestMatchers("/admin").hasRole("ADMIN")  // /admin은 ROLE_ADMIN만 접근 가능
                    .anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .httpBasic(Customizer.withDefaults())

        return http.build()
    }

}