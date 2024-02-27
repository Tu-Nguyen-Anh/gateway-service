package com.example.springproject.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * Enumeration representing different permissions in the system.
 * Each permission is associated with a specific action or resource.
 */
@RequiredArgsConstructor
public enum Permission {

    VIEW_ALL_USERS("/api/v1/users/all"),
    CREATE_USER("/api/v1/users/create"),
    UPDATE_USER("/api/v1/users/update/{id}"),
    VIEW_USER_DETAILS("/api/v1/users/get/{id}"),
    VIEW_SERVICE1("service1")
    ;

    @Getter
    private final String endPoints;


}