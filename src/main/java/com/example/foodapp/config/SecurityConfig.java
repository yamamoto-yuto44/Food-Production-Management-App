package com.example.foodapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.authorizeHttpRequests(auth -> auth
						// ログインは誰でもOK
						.requestMatchers("/login").permitAll()

						// 発注管理はADMINのみ
						.requestMatchers("/orders/**").hasRole("ADMIN")
						.requestMatchers("/materials/*/order").hasRole("ADMIN")

						// レシピ新規登録・削除はADMINのみ
						.requestMatchers("/recipes/create").hasRole("ADMIN")
						.requestMatchers("/recipes/*/delete").hasRole("ADMIN")

						// 原料の新規登録・削除はADMINのみ
						.requestMatchers("/materials/create").hasRole("ADMIN")
						.requestMatchers("/materials/*/delete").hasRole("ADMIN")

						// それ以外はログインしていればOK
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/", true)
						.permitAll())
				.logout(logout -> logout
						.logoutSuccessUrl("/login?logout"))

				.exceptionHandling(ex -> ex
						.accessDeniedPage("/error/403"));

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {

		UserDetails admin = User.withUsername("admin")
				.password(passwordEncoder().encode("password"))
				.roles("ADMIN")
				.build();

		UserDetails user = User.withUsername("user")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(admin, user);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
