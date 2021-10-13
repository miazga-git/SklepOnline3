package wat.ecommerce.SklepOnline.Security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/checkuser").hasAuthority("USER")
                .antMatchers("/h2-console/***").permitAll()//na potrzeby debugowania.hasRole("ADMIN")
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/iteminfo").permitAll()
                .antMatchers("/api/userinfo").permitAll()
                .antMatchers("/api/deleteorder/*").hasAuthority("USER")
                .antMatchers("/api/adduser").permitAll()
                .and()
                .exceptionHandling()
                .and()
                .httpBasic();
        http.headers().frameOptions().disable();
    }

 	private final UserRepositoryUserDetailsService userRepositoryUserDetailsService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  DataSource dataSource;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.userDetailsService(userRepositoryUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);

    }


}