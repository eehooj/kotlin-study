package com.example.kotlinsecurity.common.authority

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                //it.requestMatchers("/api/member/signup", "/h2-console/*").anonymous().anyRequest().permitAll()
                it.requestMatchers(
                    AntPathRequestMatcher("/api/member/signup", "/h2-console/*"))
                    .anonymous().anyRequest().permitAll() // 3.X부터 어떤 서블렛 쓸 것인지 명시 필요
            }
            .addFilterBefore(
                JwtAuthenticFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            // 뒤에 필터 보다 앞의 필터를 먼저 실행. JwtAuthenticFilter가 통과되면 UsernamePasswordAuthenticationFilter 실행X
            )

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()
}