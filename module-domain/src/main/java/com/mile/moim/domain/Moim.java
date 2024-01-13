package com.mile.moim.domain;

import com.mile.config.BaseTimeEntity;
import com.mile.user.domain.User;
import com.mile.writerName.domain.WriterName;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Moim extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private WriterName owner;
    private String name;
    private String imageUrl;
    private String information;
    private boolean isPublic;
}
