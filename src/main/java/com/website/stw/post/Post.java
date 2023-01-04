package com.website.stw.post;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdDate;

    private Integer maxNum; // 정원
    private Integer curNum; // 현재 인원

//    User Entity에 포함시키기
//    @Column(length = 11)
//    private String phone;
}
