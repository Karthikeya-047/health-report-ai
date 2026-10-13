package com.mediexplain.service;

import com.mediexplain.repo.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    private final UserRepository users;
    public DatabaseUserDetailsService(UserRepository users) { this.users = users; }
    @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = users.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
        return User.withUsername(user.getEmail()).password(user.getPassword()).roles("USER").build();
    }
}
