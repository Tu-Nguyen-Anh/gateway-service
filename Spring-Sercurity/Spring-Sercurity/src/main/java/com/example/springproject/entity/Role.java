package com.example.springproject.entity;

import com.example.springproject.constant.CommonConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static com.example.springproject.entity.Permission.*;

/**
 * Enumeration representing different roles in the system.
 * Each role has a corresponding set of permissions associated with it.
 * Provides a method to retrieve authorities for Spring Security.
 */
@RequiredArgsConstructor
public enum Role {
    USER(Set.of(
          VIEW_USER_DETAILS
    )),
    ADMIN(Set.of(
          VIEW_ALL_USERS,
          CREATE_USER,
          UPDATE_USER,
          VIEW_USER_DETAILS
    )),


    ;

    @Getter
    private final Set<Permission> permissionSet;


    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = new java.util.ArrayList<>(getPermissionSet()
              .stream()
              .map(permission -> new SimpleGrantedAuthority(permission.name()))
              .toList());
        authorities.add(new SimpleGrantedAuthority(CommonConstants.AUTHORIZATION_PREFIX + this.name()));
        return authorities;
    }
}