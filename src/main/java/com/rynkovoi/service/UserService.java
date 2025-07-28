package com.rynkovoi.service;

import com.rynkovoi.model.User;

public interface UserService {

    User findByEmail(String email);
}
