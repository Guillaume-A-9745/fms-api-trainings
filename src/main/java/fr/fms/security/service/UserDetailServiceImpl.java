package fr.fms.security.service;

import fr.fms.security.entity.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private AccountService accountService;

    public UserDetailServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = accountService.findUserByUsername(username);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach( role ->{
            authorities.add( new SimpleGrantedAuthority(role.getRolename() ));
        });
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
