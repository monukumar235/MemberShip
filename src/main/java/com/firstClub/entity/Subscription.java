package com.firstClub.entity;

import com.firstClub.constants.PlanPeriod;
import com.firstClub.constants.SubscriptionStatus;
import com.firstClub.constants.Tier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "subscription",indexes = {@Index(columnList = "userId,status")})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id",nullable = false)
    private Long userId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "period")
    private PlanPeriod period;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "status")
    private SubscriptionStatus status;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "tier")
    private Tier tier;
    @Column(nullable = false,name = "start_date")
    private LocalDate startDate;
    @Column(nullable = false,name = "end_date")
    private LocalDate endDate;
    @Version
    @Column(name = "version")
    private Long version;
    @CreatedDate
    @Column(name = "created_at")
    private OffsetDateTime createdAt  = OffsetDateTime.now(ZoneOffset.UTC);
    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt = OffsetDateTime.now(ZoneOffset.UTC);


}
