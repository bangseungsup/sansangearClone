package com.clone.sansansgear.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String userId;

    @Column
    private String url;

    @Column
    private Long price;

    @Column
    private Long size;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bucket> bucketList = new ArrayList<>();


//    public String getUserId() { return this.userId; }
}
