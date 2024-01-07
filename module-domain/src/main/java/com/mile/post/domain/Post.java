package com.mile.post.domain;

import com.mile.config.BaseTimeEntity;
import com.mile.topic.domain.Topic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Topic topic;
    private String title;
    private String content;
    private String imageUrl;
    private int curiousCount;
    private boolean anonymous;
    private boolean isTemporary;
}
