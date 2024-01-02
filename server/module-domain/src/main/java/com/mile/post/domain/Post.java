package com.mile.post.domain;

import com.mile.content.domain.Content;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Content content;
    private String title;
    private String writings;
    private String image;
    private int curiousCount;
    private boolean anonymous;
    private boolean isTemporary;
}
