package seguros.ti.gestionar.semilla;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	   protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
			.authorizeRequests().antMatchers("/**").permitAll()
			.antMatchers(HttpMethod.POST).permitAll()
			.antMatchers(HttpMethod.PATCH).permitAll()
			.antMatchers(HttpMethod.PUT).permitAll()
			.antMatchers(HttpMethod.GET).permitAll()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.antMatchers(HttpMethod.DELETE).permitAll()
			.anyRequest().permitAll()
			.and()
			.headers()	        
			.addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy","default-src 'self'; script-src 'self' 'unsafe-eval' cdn.pendo.io; connect-src 'self' app.pendo.io; img-src 'self' data: cdn.pendo.io app.pendo.io; style-src 'self' 'unsafe-inline' app.pendo.io cdn.pendo.io"))
			.httpStrictTransportSecurity()
			.includeSubDomains(true)
			.maxAgeInSeconds(31536000);
			http.cors();
	   }
}