package com.fellowtraveler.security;


import com.fellowtraveler.converter.JWTAuthenticationFilter;
import com.fellowtraveler.converter.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


/**
 * Created by igorkasyanenko on 04.03.17.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customUserDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    TokenAuthenticationManager tokenAuthenticationManager;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());


    }

    @Bean(name = "restTokenAuthenticationFilter")
    public JWTLoginFilter restTokenAuthenticationFilter() {
        JWTLoginFilter restTokenAuthenticationFilter = new JWTLoginFilter();
        // tokenAuthenticationManager.setUserDetailsService(userDetailsService);
        restTokenAuthenticationFilter.setAuthenticationManager(tokenAuthenticationManager);
        return restTokenAuthenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Create a default account

        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable caching


        http.csrf().disable()
                .headers().frameOptions().sameOrigin()
                .and()

                .addFilterAfter(restTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(), JWTLoginFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/signup").permitAll()

                .antMatchers(HttpMethod.GET, "/static/**").permitAll()
                .antMatchers(HttpMethod.POST, "/signin").permitAll()
                .antMatchers("/*").authenticated();


//        http.csrf().disable() // disable csrf for our requests.
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers(HttpMethod.POST,"/signup").permitAll()
//                .antMatchers(HttpMethod.GET,"/static/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/signin").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                // We filter the api/login requests
//                .addFilterAfter(new JWTLoginFilter("/rest/**", tokenAuthenticationManager), UsernamePasswordAuthenticationFilter.class);
//
//                // And filter other requests to check the presence of JWT in header
//               // .addFilterAfter(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}