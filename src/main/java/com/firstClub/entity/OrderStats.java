package com.firstClub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.annotation.Target;
import java.math.BigDecimal;

@Entity
@Table(name = "order_stats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false,unique = true,name = "user_id")
    private Long userId;
    @Column(nullable = false,name = "monthly_order_count")
    private int monthlyOrderCount;
    @Column(nullable = false ,precision = 14,scale = 2,name = "monthly_order_value")
    private BigDecimal monthlyOrderValue;
    @Column(name = "cohort")
    private String cohort;

    public OrderStats(Long userId, int monthlyOrderCount, BigDecimal monthlyOrderValue, String  cohort) {
        this.userId = userId;
        this.monthlyOrderCount = monthlyOrderCount;
        this.monthlyOrderValue = monthlyOrderValue;
        this.cohort = cohort;

    }
}
