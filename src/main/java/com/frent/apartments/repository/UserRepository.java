package com.frent.apartments.repository;

import com.frent.apartments.model.Listing;
import com.frent.apartments.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
