package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.isEnabled;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserServiceImpl userService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserServiceImpl userService) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
    }

    //конфиг разграничения доступа по прилетающему url
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()

                //приоритет админу
                .antMatchers("/api/admin**").hasAuthority("ADMIN")
                .antMatchers("/api/user**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/admin").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/favicon.ico").hasAnyAuthority("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()

                //возможность залогиниться - юзеру с любой ролью
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and().logout().permitAll();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return true;
            }
        };
    }

/*
    DaoAuthenticationProvider — это реализация AuthenticationProvider, которая использует UserDetailsService и
    PasswordEncoder для проверки подлинности имени пользователя и пароля.
*/
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
}