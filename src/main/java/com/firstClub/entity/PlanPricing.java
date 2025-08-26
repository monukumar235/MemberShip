package com.firstClub.entity;

import com.firstClub.constants.PlanPeriod;
import com.firstClub.constants.Tier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "plan_pricing",uniqueConstraints = {@UniqueConstraint(columnNames = {"period","tier"})})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanPricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "period")
    private PlanPeriod period;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "tier")
    private Tier tier;

    @Column(nullable = false,precision = 12,scale = 2,name = "price")
    private BigDecimal price;



    public PlanPricing(PlanPeriod period, Tier tier, BigDecimal price) {
        this.period = period;
        this.tier = tier;
        this.price = price;
    }
}
