package dev.nemi.aoharu.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@Configuration
//@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class CustomSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    log.info("----------configure----------");

    http.formLogin(
      s -> s.loginPage("/login")
    );

//    http.csrf( s-> s.disable() );
    http.authorizeHttpRequests(
      auth -> {
        auth.requestMatchers("/css/**", "/js/**", "/img/**", "/login").permitAll();
        auth.requestMatchers("/board/write", "/board/edit/*", "/food/register", "/food/edit/*").authenticated();
//        auth.requestMatchers("/admin/**").hasRole("ADMIN");
        auth.anyRequest().authenticated();
      }
    );
    return http.build();

  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {

    log.info("----------web configure----------");
    return web -> web.ignoring().requestMatchers(
      PathRequest.toStaticResources().atCommonLocations()
    );

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
