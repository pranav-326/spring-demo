package com.pranavlearn.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository ur;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository ur, PasswordEncoder passwordEncoder) { this.ur=ur;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ur.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username "+ username + " Not Found"));
    }
    public void register(User user) {
        ur.save(new User(user.getUsername(), passwordEncoder.encode(user.getPassword())));
    }
}
