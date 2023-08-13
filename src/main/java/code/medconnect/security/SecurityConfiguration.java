package code.medconnect.security;

import jakarta.servlet.DispatcherType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

/*    @Bean
    public AuthenticationManager authManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailService
    )
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }*/
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailService
    ) throws Exception {
            var authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailService);
            authProvider.setPasswordEncoder(passwordEncoder);
            return new ProviderManager(authProvider);
    }


    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "true", matchIfMissing = true)
    SecurityFilterChain securityEnabled(HttpSecurity http) throws Exception {

        CookieClearingLogoutHandler cookies = new CookieClearingLogoutHandler("JSESSIONID");

        return http
                .authorizeHttpRequests(auth -> {
                            auth.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll();
                            auth.requestMatchers(
                                    "/static/**", "/home", "/register", "/ourdoctors")
                                    .permitAll();
                            auth.requestMatchers("/doctor").hasRole("DOCTOR");
                            auth.requestMatchers("/patient").hasRole("PATIENT");
                            auth.anyRequest().permitAll();

                        })
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/home"))
                .logout((logout) -> logout.addLogoutHandler(cookies))
                .build();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "false")
    SecurityFilterChain securityDisabled(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth ->
                auth.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
        }

}

