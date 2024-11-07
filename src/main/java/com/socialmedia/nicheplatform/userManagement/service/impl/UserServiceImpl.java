package com.socialmedia.nicheplatform.userManagement.service.impl;

import com.socialmedia.nicheplatform.Security.JwtTokenProvider;
import com.socialmedia.nicheplatform.userManagement.model.User;
import com.socialmedia.nicheplatform.userManagement.model.UserRole;
import com.socialmedia.nicheplatform.userManagement.model.VerificationToken;
import com.socialmedia.nicheplatform.userManagement.repository.UserRepository;
import com.socialmedia.nicheplatform.userManagement.repository.VerificationTokenRepository;
import com.socialmedia.nicheplatform.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public User  registerUser(String email, String password, String name) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // Create new user with encoded password
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        // Assign default role
        user.setRoles(Collections.singleton(new UserRole(user.getId(), "ROLE_USER")));
        this.save(user);
        return user;
    }

    @Override
    public String loginUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            logger.info("Authenticated Principles : {}", authentication.getPrincipal().toString());
            return tokenProvider.generateJwtToken((UserDetails) authentication.getPrincipal());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
    }

    @Override
    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)); // 24-hour expiry
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Error: Token not found or Link has been verified."));
    }

    public void deleteVerificationToken(VerificationToken token) {
        verificationTokenRepository.delete(token);
    }


}
