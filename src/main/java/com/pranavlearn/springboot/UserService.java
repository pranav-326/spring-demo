package com.pranavlearn.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository ur;

    @Autowired
    public UserService(UserRepository ur) { this.ur=ur; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ur.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username "+ username + " Not Found"));
    }
}
