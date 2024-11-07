package com.team4.readit.domain.highlight.domain;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.user_info.domain.UserInfo;
import com.team4.readit.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE highlight SET status = 'DELETED' WHERE id = ?")
@SQLRestriction("status = 'ACTIVE'")
public class Highlight extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @Column(columnDefinition = "TEXT")
    private String memoText;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int startIndex;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int endIndex;
}
