package com.clone.sansansgear.repository;

import com.clone.sansansgear.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

//    boolean existsByUserId(String userId);
//    void deleteByUserId(String userId);

}
