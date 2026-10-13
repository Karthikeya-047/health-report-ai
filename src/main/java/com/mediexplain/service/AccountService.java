package com.mediexplain.service;
import com.mediexplain.model.AppUser; import com.mediexplain.repo.UserRepository; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service public class AccountService {
 private final UserRepository users; private final PasswordEncoder encoder;
 public AccountService(UserRepository users,PasswordEncoder encoder){this.users=users;this.encoder=encoder;}
 @Transactional public void register(String name,String email,String password){String normalized=email.trim().toLowerCase();if(users.findByEmailIgnoreCase(normalized).isPresent())throw new IllegalArgumentException("An account with this email already exists.");users.save(new AppUser(name.trim(),normalized,encoder.encode(password)));}
 public AppUser current(String email){return users.findByEmailIgnoreCase(email).orElseThrow();}
}
