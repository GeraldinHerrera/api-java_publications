package com.app.publications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "publications_network_postcontent")
public class PostContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file", nullable = false)
    private String file;

    @Column(name = "caption", columnDefinition = "TEXT")
    private String caption;

    @JsonProperty("post_type")
    @Column(name = "post_type", length = 10, nullable = false)
    private String postType = "post";

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = OffsetDateTime.now();
        }
    }

    public PostContent() {}

    public PostContent(String file, String caption, String postType) {
        this.file = file;
        this.caption = caption;
        this.postType = postType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
