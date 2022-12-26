package com.clone.sansansgear.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Bucket {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column
    private Long productNum;

    @Column
    private Long size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


}
