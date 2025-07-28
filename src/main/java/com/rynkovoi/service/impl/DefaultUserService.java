package com.rynkovoi.service.impl;

import com.rynkovoi.exception.UserNotFoundException;
import com.rynkovoi.model.User;
import com.rynkovoi.repository.UserRepository;
import com.rynkovoi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService  implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: %s".formatted( email)));
    }
}
