package com.example.springproject.service;

import com.example.springproject.dto.base.PageResponse;
import com.example.springproject.dto.request.UserRequest;
import com.example.springproject.dto.request.UserUpdateRequest;
import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.dto.response.UserUpdateResponse;

/**
 * Service interface defining operations related to User entities.
 */
public interface UserService {
  UserResponse create(UserRequest request);

  UserUpdateResponse update(String id, UserUpdateRequest request);

  void delete(String id);

  PageResponse<UserResponse> viewAll(int size, int page);

  UserResponse getUserById(String id);
}