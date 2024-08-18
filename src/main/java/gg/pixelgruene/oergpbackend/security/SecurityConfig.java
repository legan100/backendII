package gg.pixelgruene.oergpbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests->{
                    //groupnames immer groß
                    authorizeRequests.requestMatchers("/api/users/create").permitAll();
                    authorizeRequests.requestMatchers("/admin/").hasRole("ADMIN");
                    authorizeRequests.requestMatchers("/admin/").hasRole("FRONTENDDEV");
                }).addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public TokenFilter tokenAuthenticationFilter() {
        return new TokenFilter();
    }
}
