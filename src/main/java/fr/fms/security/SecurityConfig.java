package fr.fms.security;

import fr.fms.security.entity.AppUser;
import fr.fms.security.service.AccountService;
import fr.fms.security.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private AccountService accountService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                AppUser user = accountService.findUserByUsername(username);
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                user.getRoles().forEach( role ->{
                    authorities.add( new SimpleGrantedAuthority(role.getRolename() ));
                });
                return new User(user.getUsername(), user.getPassword(), authorities);
            }
        });
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return  new BCryptPasswordEncoder();
    }

    @Override
    protected  void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
    }
}

