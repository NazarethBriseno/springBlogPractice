package com.example.naztestblog.repositories;

import com.example.naztestblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>{
    Post findById(long id);

    public List<Post> findAllByOrderByIdDesc();
}
