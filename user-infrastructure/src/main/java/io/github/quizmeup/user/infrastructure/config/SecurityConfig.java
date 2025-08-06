package io.github.quizmeup.user.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import static io.github.quizmeup.user.infrastructure.resource.UserResource.USER_CREATION_ENDPOINT;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain userCreationSecurityFilterChain(final HttpSecurity httpSecurity,
                                                               @Qualifier("defaultCorsConfigurationSource") final CorsConfigurationSource corsConfigurationSource) throws Exception {
        return httpSecurity
                .securityMatcher(httpServletRequest -> httpServletRequest.getRequestURI().equals(USER_CREATION_ENDPOINT) && HttpMethod.POST.matches(httpServletRequest.getMethod()))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource))
                .build();
    }
}
