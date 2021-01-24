package com.bitinc.rest.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug=true)
@EnableGlobalMethodSecurity(
    // securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private PokeUserDetailsService userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        .antMatchers("/api/auth/**").permitAll()
        .antMatchers(HttpMethod.GET, "/rest/v1/pokemon/").permitAll()
        .antMatchers(HttpMethod.GET, "/rest/v1/pokemon/*").authenticated()
        .antMatchers(HttpMethod.GET,"/rest/v1/user/").permitAll()
        .anyRequest().authenticated();

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    System.out.println("In SecurityConfig.configure(AuthenticationManagerBuilder)");

//    auth.authenticationProvider(authenticationProvider());
    auth
//        .userDetailsService(userDetailsService())
//        .passwordEncoder(passwordEncoder());
        .userDetailsService(userDetailsService)
        .passwordEncoder(userDetailsService.getEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }


//  @Bean
//  @Override
//  protected UserDetailsService userDetailsService() {
//    return new PokeUserDetailsService();
//  }
//
//  @Bean
//  protected BCryptPasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }

//  @Bean
//  public DaoAuthenticationProvider authenticationProvider(){
//    System.out.println("In authenticationProvider");
//
//    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//    provider.setPasswordEncoder(myUserDetailsService.getEncoder());
//    provider.setUserDetailsService(myUserDetailsService);
//    return provider;
//  }
}