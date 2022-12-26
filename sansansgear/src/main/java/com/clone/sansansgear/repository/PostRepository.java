package com.clone.sansansgear.repository;

import com.clone.sansansgear.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    boolean existsByIdAndUserId(Long id, String userId);

    void deleteAllByUserId(String userId);
}
