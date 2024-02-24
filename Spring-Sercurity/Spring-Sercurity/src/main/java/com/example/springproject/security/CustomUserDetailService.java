package com.example.springproject.security;

import com.example.springproject.entity.User;
import com.example.springproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * CustomUserDetailService class implements the UserDetailsService interface to provide custom user details retrieval logic.
 * It is responsible for loading user details from the UserRepository based on the provided username.
 */
@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Load user details by username from the UserRepository.
     *
     * @param username the username of the user to load
     * @return UserDetails containing the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Can't find user!");
        }
        return new CustomUserDetail(userOptional.get());
    }
}