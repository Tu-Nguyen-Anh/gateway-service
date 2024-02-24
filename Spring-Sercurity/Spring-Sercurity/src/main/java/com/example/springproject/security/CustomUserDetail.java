package com.example.springproject.security;

import com.example.springproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * CustomUserDetail class implements the UserDetails interface for custom user details in Spring Security.
 * It holds information about the authenticated user, including username, password, and authorities.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomUserDetail implements UserDetails {

    /**
     * The user entity associated with the authenticated user.
     */
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole().getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Assume user accounts never expire.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Assume user accounts are never locked.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Assume user credentials never expire.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Assume user accounts are always enabled.
        return true;
    }
}