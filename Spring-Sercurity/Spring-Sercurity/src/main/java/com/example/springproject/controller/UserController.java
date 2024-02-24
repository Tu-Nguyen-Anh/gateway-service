package com.example.springproject.controller;

import com.example.springproject.dto.base.PageResponse;
import com.example.springproject.dto.base.ResponseGeneral;
import com.example.springproject.dto.request.UserRequest;
import com.example.springproject.dto.request.UserUpdateRequest;
import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.dto.response.UserUpdateResponse;
import com.example.springproject.service.UserService;
import com.example.springproject.service.base.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.springproject.constant.CommonConstants.*;
import static com.example.springproject.constant.MessageCodeConstant.*;

/**
 * Controller class for handling user-related operations in the RESTful API.
 *
 * This class is annotated with `@RestController`, indicating that it processes REST requests and produces JSON responses.
 * It uses the Lombok annotations `@Slf4j` for logging and `@RequiredArgsConstructor` for automatically injecting dependencies.
 * The base request mapping for all endpoints in this controller is "/api/v1/users".
 *
 * The class includes methods to perform CRUD operations on user entities, such as retrieving a user by ID, creating a new user,
 * searching for users, retrieving all users, and deleting a user. Each method produces a response wrapped in the `ResponseGeneral` class,
 * which follows a standardized format including a status, message, and data payload.
 *
 * The `getById` method handles GET requests to retrieve a user by ID, while the `create` method handles POST requests to create a new user.
 * The `getUserBySearch` and `getAllUser` methods handle GET requests to search for users based on a keyword and retrieve all users, respectively.
 * The `delete` method handles DELETE requests to delete a user by ID.
 *
 * Request parameters, such as ID, keyword, size, and page, are specified using annotations like `@PathVariable` and `@RequestParam`.
 * Language information is extracted from the request header and used for localized message retrieval via the `MessageService`.
 *
 * Logging statements are included using the SLF4J logger to capture relevant information for each operation.
 *
 * Note: The class demonstrates the use of standard HTTP methods, request parameters, and response structures for a user-related API.
 *
 * @author [nguyenanhtu123]
 * @version 1.0
 * @since [1/6/2023]
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserService userService;
  private final MessageService messageService;

  /**
   * Handles POST requests to create a new user.
   *
   * @param request  The request body containing user creation details.
   * @param language The language for message localization.
   * @return A ResponseEntity with a standardized response containing the localized message and the created user data.
   */
  @PostMapping("/create")
  public ResponseGeneral<UserResponse> create(
        @Validated
        @RequestBody UserRequest request,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(create) Request : {}", request);
    return ResponseGeneral.ofCreated(messageService.getMessage(CREATE_USER, language),
          userService.create(request));
  }

  /**
   * Handles PUT requests to update a user by ID.
   *
   * @param request  The request body containing updated user information.
   * @param id       The ID of the user to update.
   * @param language The language for message localization.
   * @return A ResponseEntity with a standardized response containing the localized success message and updated user data.
   */
  @PutMapping("/update/{id}")
  public ResponseGeneral<UserUpdateResponse> update(
        @Validated
        @RequestBody UserUpdateRequest request,
        @PathVariable String id,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(create) Request : {}", request);
    return ResponseGeneral.ofSuccess(messageService.getMessage(UPDATE_USER, language),
          userService.update(id,request));
  }

  /**
   * Handles GET requests to retrieve all users.
   *
   * @param size     The number of users to include in each page of the result.
   * @param page     The page number of the result to retrieve.
   * @param language The language for message localization.
   * @return A ResponseEntity with a standardized response containing the localized message and a paginated list of all users.
   */
  @GetMapping("/all")
  public ResponseGeneral<PageResponse<UserResponse>> viewAllUser(
        @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) int page,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(listAllUser) size : {}, page: {}", size, page);
    return ResponseGeneral.ofSuccess(messageService.getMessage(LIST_USER, language),
          userService.viewAll(size, page)
    );
  }

  /**
   * Handles DELETE requests to delete a user by ID.
   *
   * @param id       The ID of the user to delete.
   * @param language The language for message localization.
   * @return A ResponseEntity with a standardized response containing the localized success message.
   */
  @DeleteMapping("/delete/{id}")
  public ResponseGeneral<Void> delete(
        @PathVariable String id,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(delete) id : {}", id);
    userService.delete(id);
    return ResponseGeneral.ofSuccess(messageService.getMessage(DELETE_USER, language));
  }

  /**
   * Handles GET requests to retrieve a user by ID.
   *
   * @param id       The ID of the user to retrieve.
   * @param language The language for message localization.
   * @return A ResponseEntity with a standardized response containing the localized success message and user data.
   */
  @GetMapping("/get/{id}")
  public ResponseGeneral<UserResponse> getUserById(
        @PathVariable String id,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ){
    return ResponseGeneral.ofSuccess(messageService.getMessage(GET_USER, language), userService.getUserById(id));
  }
}