package com.spring.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public AuthenticationProvider authProvider() {
		
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		
		/* it is used for without taking bcrypt */
		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		
		/* it is using with Bcrypt it decode the encrypt password from database */
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		return provider;
	}

	//this method is user to create our own login from want to be excute
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

           http
               .csrf().disable()
               .authorizeRequests().antMatchers("/login").permitAll()
               .anyRequest().authenticated()
               .and()
               .formLogin()
               .loginPage("/login").permitAll()
               .and()
               .logout().invalidateHttpSession(true)
               .clearAuthentication(true)
               .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
               .logoutSuccessUrl("/logout-success").permitAll();
	}
	
	
	
	
	
	
	
	/* ctrl+shift+O to remove unwnated packeage */
	
	/* it is user for to provide our own username and password as hard-coded */
	
//	@Bean
//	@Override
//	protected UserDetailsService userDetailsService() {
//
//       List<UserDetails> users=new ArrayList<UserDetails>();
//       users.add(User.withDefaultPasswordEncoder().username("rakesh").password("12345").roles("USER").build());
//       
//       return new InMemoryUserDetailsManager(users);
//	}

	
	
}
