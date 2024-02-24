package com.example.springproject.controller;

import com.example.springproject.dto.base.ResponseGeneral;
import com.example.springproject.dto.request.AuthenticationRequest;
import com.example.springproject.dto.request.UserRequest;
import com.example.springproject.dto.response.AuthenticationResponse;
import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.service.base.MessageService;
import com.example.springproject.service.base.MessageServiceImpl;
import com.example.springproject.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.springproject.constant.CommonConstants.DEFAULT_LANGUAGE;
import static com.example.springproject.constant.CommonConstants.LANGUAGE;
import static com.example.springproject.constant.MessageCodeConstant.LOGIN_CODE;
import static com.example.springproject.constant.MessageCodeConstant.REGISTER_CODE;

/**
 * Controller class handling authentication-related endpoints.
 * This class provides endpoints for user registration and login.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MessageService messageService;

    private final AuthenticationService authenticationService;

    /**
     * Endpoint for registering a new user.
     *
     * @param userRequest Information about the user to be registered.
     * @param language    The language for message localization.
     * @return ResponseGeneral containing information about user registration and corresponding message.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseGeneral<UserResponse> register(@Validated @RequestBody UserRequest userRequest,
                                                  @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language){
        return ResponseGeneral.ofCreated(messageService.getMessage(REGISTER_CODE, language), authenticationService.register(userRequest));
    }

    /**
     * Endpoint for user login.
     *
     * @param authenticationRequest User authentication information.
     * @param language              The language for message localization.
     * @return ResponseGeneral containing information about user login and corresponding message.
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseGeneral<AuthenticationResponse> logIn(@Validated @RequestBody AuthenticationRequest authenticationRequest,
                                                         @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language){
        return ResponseGeneral.ofSuccess(messageService.getMessage(LOGIN_CODE, language), authenticationService.logIn(authenticationRequest));
    }



}
