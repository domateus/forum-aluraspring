package dev.me.forum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.me.forum.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfigs extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthService authService;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UsuarioRepository repository;

  // Configurações de autenticação
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());

  }

  // Configurações de recursos estáticos
  @Override
  public void configure(WebSecurity web) throws Exception {

  }

  // Configurações de autorização
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers(HttpMethod.GET, "/topicos").permitAll()
        .antMatchers(HttpMethod.GET, "/topicos/*").permitAll().antMatchers(HttpMethod.POST, "/auth").permitAll()
        .antMatchers("/h2-console/**").permitAll().anyRequest().authenticated().and().csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().headers().frameOptions()
        .disable();

    http.addFilterBefore(new TokenFilterAuth(tokenService, repository), UsernamePasswordAuthenticationFilter.class);

  }

  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

}
