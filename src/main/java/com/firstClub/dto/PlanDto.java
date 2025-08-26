package com.firstClub.dto;

import com.firstClub.entity.PlanPricing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanDto {
    private String period;
    private String tier;
    private BigDecimal price;

    public PlanDto(PlanPricing planPricing) {
        this.period = planPricing.getPeriod().toString();
        this.tier = planPricing.getTier().toString();
        this.price = planPricing.getPrice();
    }
}
