package com.app.publications.controller;

import com.app.publications.model.PostContent;
import com.app.publications.repository.PostContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostContentController {

    @Autowired
    private PostContentRepository repository;

    @GetMapping
    public ResponseEntity<List<PostContent>> getAllPosts() {
        List<PostContent> posts = repository.findAllByOrderByIdDesc();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostContent> getPostById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PostContent> createPost(@RequestBody PostContent post) {
        if (post.getFile() == null || post.getFile().trim().isEmpty()) {
            post.setFile("publications_media/sample.png");
        }
        PostContent saved = repository.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostContent> updatePost(@PathVariable Long id, @RequestBody PostContent postDetails) {
        return repository.findById(id)
                .map(existingPost -> {
                    if (postDetails.getCaption() != null) {
                        existingPost.setCaption(postDetails.getCaption());
                    }
                    if (postDetails.getPostType() != null) {
                        existingPost.setPostType(postDetails.getPostType());
                    }
                    if (postDetails.getFile() != null && !postDetails.getFile().trim().isEmpty()) {
                        existingPost.setFile(postDetails.getFile());
                    }
                    PostContent updated = repository.save(existingPost);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
