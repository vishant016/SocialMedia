package com.socialmedia.nicheplatform.userManagement.service;

import com.socialmedia.nicheplatform.userManagement.model.User;
import com.socialmedia.nicheplatform.userManagement.model.VerificationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService{
    User registerUser(String email, String password, String name);
    String loginUser(String email, String password);
    User save(User user);
    User getUserByEmail(String email);
    String generateVerificationToken(User user);
    VerificationToken getVerificationToken(String token);
    void deleteVerificationToken(VerificationToken token);
}
