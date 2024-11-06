package com.team4.readit.domain.user_info.domain;

import com.team4.readit.domain.job.domain.Job;
import com.team4.readit.domain.scrap.domain.Scrap;
import com.team4.readit.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE user_info SET status = 'DELETED' WHERE id = ?")
@SQLRestriction("status = 'ACTIVE'")
public class UserInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Scrap> scraps;
}


