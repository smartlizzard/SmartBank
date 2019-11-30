package com.smartbank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.smartbank.service.UserSecurityService;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;

	
	@Autowired
	private UserSecurityService userSecurityService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	
	
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * 
	 * auth.inMemoryAuthentication()
	 * .withUser("user").password("{noop}password").roles("USER") .and()
	 * .withUser("admin").password("{noop}password").roles("USER", "ADMIN");
	 * 
	 * }
	 */
	
	private static final String[] PUBLIC_MATCHERS = {  "/error/**/*","/login/**", "/register/**","/account/getAccDtls/**","/account/txByClerk"};
	
	//private static final String[] PUBLIC_MATCHERS = {  "*" };
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        // your configuration goes here
        http
            .authorizeRequests()
            //antMatchers("/**").
			.antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();
        
        http
        .httpBasic()
//				/* .and().formLogin().defaultSuccessUrl("/userDetail", true) */
        .and().csrf().disable()
        .cors().disable()
       ;
    }

	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    	 auth.inMemoryAuthentication().withUser("user").password("password").roles("USER"); //This is in-memory authentication
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
}
