package org.virtual.ai.service;


import org.virtual.ai.domain.LoginResponse;
import org.virtual.ai.domain.UserInfoResponse;
import org.virtual.ai.domain.bo.LoginRequest;

public interface IUserService {

    LoginResponse login(LoginRequest loginRequest);

    UserInfoResponse info();


}
