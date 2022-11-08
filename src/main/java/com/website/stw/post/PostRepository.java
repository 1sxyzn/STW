package com.website.stw.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository <Post, Integer> { // entity & pk type

}
