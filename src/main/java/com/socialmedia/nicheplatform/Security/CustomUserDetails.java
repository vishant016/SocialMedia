package com.socialmedia.nicheplatform.Security;

import com.socialmedia.nicheplatform.userManagement.model.User;
import com.socialmedia.nicheplatform.userManagement.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final User user;
    private final Set<UserRole> roles;
    public CustomUserDetails(User user, Set<UserRole> roles) {
        this.user = user;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Here, you can fetch user roles or authorities
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet()); // if `getRoles()` returns `Collection<GrantedAuthority>`
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Use email or any unique field for login
    }
}

