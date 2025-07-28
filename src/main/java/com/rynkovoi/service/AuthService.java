package com.rynkovoi.service;

import com.rynkovoi.common.request.AuthenticateRequest;
import com.rynkovoi.common.response.AuthenticateResponse;

public interface AuthService {

    AuthenticateResponse authenticate(AuthenticateRequest request) throws Exception;
}
