package org.cn.userservice.service.impl;

import org.cn.userservice.dao.UserRepository;
import org.cn.userservice.entity.Utilisateur;
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
public class UserDetailService implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur u = repository.findByEmail(email);
        if(u == null) new UsernameNotFoundException("User not found with username: " + email);

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        assert u != null;
        authorities.add(new SimpleGrantedAuthority(u.getRole()));
        return new User(u.getEmail(), u.getPassword(), authorities);
    }

}
