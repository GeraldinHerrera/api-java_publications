package com.app.publications.repository;

import com.app.publications.model.PostContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostContentRepository extends JpaRepository<PostContent, Long> {
    List<PostContent> findAllByOrderByIdDesc();
}
