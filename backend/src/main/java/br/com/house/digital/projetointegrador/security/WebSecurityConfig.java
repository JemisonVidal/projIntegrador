package br.com.house.digital.projetointegrador.security;

import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.service.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsServiceImpl jwtUserDetailsService;
    private final JWTRequestFilter jwtRequestFilter;

    public boolean checkUserId(Authentication authentication, UserType type, Long id) {
        User user = (User) authentication.getPrincipal();
        return user.getType().equals(type) && user.getProfileId().equals(id);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().cors().and()
            .authorizeRequests()
            .antMatchers("/**/api/authenticate", "/**/api/register").permitAll()
            .antMatchers(HttpMethod.GET, "/**/api/opportunity/{id}").permitAll()
            .antMatchers(HttpMethod.GET, "/**/avatar").permitAll()
            .antMatchers(HttpMethod.PATCH, "/**/api/profile/{type}/{id}/**").access("@webSecurityConfig.checkUserId(authentication,#type.toUpperCase(),#id)")
            .antMatchers(HttpMethod.POST, "/**/api/opportunity").hasAuthority(UserType.COMPANY.name())
            .antMatchers(HttpMethod.GET, "/**/api/opportunity/applied").hasAuthority(UserType.APPLICANT.name())
            .antMatchers(HttpMethod.GET, "/**/api/opportunity/{id}/applied").hasAuthority(UserType.COMPANY.name())
            .antMatchers(HttpMethod.POST, "/**/api/opportunity/{id}/apply").hasAuthority(UserType.APPLICANT.name())
            .antMatchers("/**/api/**").authenticated()
            .antMatchers("/", "/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}