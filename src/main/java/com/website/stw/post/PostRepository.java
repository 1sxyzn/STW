package com.website.stw.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository <Post, Integer> { // entity & pk type
    Page<Post> findAll(Pageable pageable);
}
