package com.rynkovoi.repository.jpa;

import com.rynkovoi.model.User;
import com.rynkovoi.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
}
