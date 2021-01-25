package com.bitinc.rest.configs;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import com.bitinc.rest.security.jwt.AuthTokenFilter;
import com.bitinc.rest.security.jwt.AuthEntryPointJwt;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import com.bitinc.rest.security.services.UserDetailsServiceImpl;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
//@EnableWebSecurity
@EnableWebSecurity(debug=true)
@EnableGlobalMethodSecurity(
//     securedEnabled = true
//    jsr250Enabled = true
    prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserDetailsServiceImpl userDetailsService;

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
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .authorizeRequests()
        .antMatchers("/api/auth/**").permitAll()
        .antMatchers("/api/test/**").permitAll()
        .antMatchers(HttpMethod.GET, "/rest/v1/pokemon/").permitAll()
        .antMatchers(HttpMethod.GET, "/rest/v1/pokemon/*").authenticated()
        .antMatchers(HttpMethod.GET,"/rest/v1/user/").permitAll()
        .anyRequest().authenticated();

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Override
//  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(userDetailsService)
        .passwordEncoder(userDetailsService.getEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // TokenAuthenticationFilter will ignore the below paths
    web.ignoring().antMatchers(
        HttpMethod.POST,
        "/auth/login"
    );
    web.ignoring().antMatchers(
        HttpMethod.GET,
        "/",
        "/webjars/**",
        "/*.html",
        "/favicon.ico",
        "/**/*.html",
        "/**/*.css",
        "/**/*.js"
    );

  }
}