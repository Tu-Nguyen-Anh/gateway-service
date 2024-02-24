package com.example.springproject.service.impl;

import com.example.springproject.dto.base.PageResponse;
import com.example.springproject.dto.request.UserRequest;
import com.example.springproject.dto.request.UserUpdateRequest;
import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.dto.response.UserUpdateResponse;
import com.example.springproject.entity.Role;
import com.example.springproject.entity.User;
import com.example.springproject.exception.base.BadRequestException;
import com.example.springproject.exception.base.DuplicateException;
import com.example.springproject.exception.base.UserNotFoundException;
import com.example.springproject.repository.UserRepository;
import com.example.springproject.security.CustomUserDetail;
import com.example.springproject.service.UserService;
import com.example.springproject.service.base.BaseServiceImpl;
import com.example.springproject.utils.DateUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.springproject.constant.CommonConstants.AUTHORIZATION_PREFIX;
import static com.example.springproject.constant.ExceptionCode.DUPLICATE_USERNAME_CODE;

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
  private final UserRepository repository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository repository, JwtService jwtService, PasswordEncoder passwordEncoder) {
    super(repository);
    this.repository = repository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * method that allows creating new users
   *
   * @param request UserRequest
   * @return UserResponse
   */

  @Transactional
  @Override
  public UserResponse create(UserRequest request) {
    DateUtils.checkDateOfBirth(request.getDateOfBirth());
    this.checkUsernameIfExist(request.getUsername());

    User user = new User(
          request.getUsername(),
          passwordEncoder.encode(request.getPassword()),
          request.getEmail(),
          request.getPhone(),
          request.getDateOfBirth()
    );

    this.create(user);

    return new UserResponse(
          user.getId(),
          user.getUsername(),
          user.getEmail(),
          user.getPhone(),
          user.getRole(),
          jwtService.generateToken(new CustomUserDetail(user)),
          user.getDateOfBirth()
    );
  }

  /**
   * method that allows update users
   *
   * @param request UserUpdateRequest, id
   * @return UserUpdateResponse
   */
  @Override
  @Transactional
  public UserUpdateResponse update(String id, UserUpdateRequest request) {

    DateUtils.checkDateOfBirth(request.getDateOfBirth());
    User user = this.checkUserExist(id);

    this.setValueForUpdate(user, request);
    this.update(user);

    return new UserUpdateResponse(
          user.getId(),
          user.getPassword(),
          user.getEmail(),
          user.getPhone(),
          user.getRole(),
          user.getDateOfBirth()
    );
  }

  /**
   * Retrieves user information by ID.
   *
   * @param id The ID of the user to retrieve.
   * @return The UserResponse containing user details.
   * @throws UserNotFoundException if the user with the specified ID is not found.
   * @throws BadRequestException   if the request is invalid.
   */
  @Override
  public UserResponse getUserById(String id) {
    CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (customUserDetail.getAuthorities().contains(new SimpleGrantedAuthority(AUTHORIZATION_PREFIX + Role.ADMIN.name()))) {
      User user = repository.findById(id).orElseThrow(UserNotFoundException::new);
      return UserResponse.builder()
            .id(user.getId())
            .phone(user.getPhone())
            .role(user.getRole())
            .username(user.getUsername())
            .email(user.getEmail())
            .dateOfBirth(user.getDateOfBirth())
            .build();
    }

    if (!id.equals(customUserDetail.getUser().getId())
        && customUserDetail.getAuthorities().contains(new SimpleGrantedAuthority(AUTHORIZATION_PREFIX + Role.USER.name()))) {
      throw new BadRequestException();
    }
    User user = repository.findById(id).orElseThrow(UserNotFoundException::new);
    return UserResponse.builder()
          .id(user.getId())
          .phone(user.getPhone())
          .role(user.getRole())
          .username(user.getUsername())
          .email(user.getEmail())
          .dateOfBirth(user.getDateOfBirth())
          .build();
  }

  /**
   * method that allows delete users
   *
   * @param id id
   */
  @Transactional
  @Override
  public void delete(String id) {
    User user = this.checkUserExist(id);
    repository.delete(user);
  }

  /**
   * method that allows view all users
   *
   * @param size, page UserRequest
   * @return PageResponse
   */
  @Override
  public PageResponse<UserResponse> viewAll(int size, int page) {
    Pageable pageable = PageRequest.of(page, size);
    Page<UserResponse> listAllUsers = repository.findAllUser(pageable);
    return PageResponse.of(listAllUsers.getContent(), (int) listAllUsers.getTotalElements());
  }


  /**
   * method that allows check Username Exist
   *
   * @param username
   */
  private void checkUsernameIfExist(String username) {
    Optional<User> optionalUser = repository.findUserByUsername(username);
    if (optionalUser.isPresent()) {
      throw new DuplicateException(DUPLICATE_USERNAME_CODE);
    }
  }

  /**
   * method that allows check User Exist
   *
   * @param id
   * @return
   */
  private User checkUserExist(String id) {
    return repository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  /**
   * method that allows set value for update
   *
   * @param user
   * @param update
   */
  private void setValueForUpdate(User user, UserUpdateRequest update) {
    user.setPassword(passwordEncoder.encode(update.getPassword()));
    user.setPhone(update.getPhone());
    user.setEmail(update.getEmail());
    user.setDateOfBirth(update.getDateOfBirth());
  }

  /**
   * Initializes the application by creating an admin user.
   * This method is annotated with @PostConstruct to ensure it runs after the bean is constructed.
   */
  @PostConstruct
  @Transactional
  void createAdmin() {
    repository.removeAdmin();
    User admin = User.builder().
          username("admin").
          password(passwordEncoder.encode("123456")).
          role(Role.ADMIN).
          build();
    repository.save(admin);
  }

  /**
   * Cleans up resources and removes the admin user during application shutdown.
   * This method is annotated with @PreDestroy to ensure it runs before the bean is destroyed.
   */
  @PreDestroy
  @Transactional
  void deleteAdmin() {
    repository.removeAdmin();
  }

}