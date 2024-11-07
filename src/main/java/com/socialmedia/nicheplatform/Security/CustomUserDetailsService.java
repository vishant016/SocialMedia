package com.socialmedia.nicheplatform.Security;

import com.socialmedia.nicheplatform.userManagement.model.User;
import com.socialmedia.nicheplatform.userManagement.model.UserRole;
import com.socialmedia.nicheplatform.userManagement.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        Set<UserRole> roles = user.getRoles();
        return new CustomUserDetails(user,roles);
    }
}

