package com.firstClub.rule;

import com.firstClub.constants.Tier;
import com.firstClub.entity.OrderStats;

import java.math.BigDecimal;
import java.util.Map;

public class MonthlySpendRule implements TierRule{
    @Override
    public Tier evaluate(Long userId, OrderStats stats, Map<String,String> params) {

        BigDecimal gold = new BigDecimal(params.getOrDefault("goldMinSpend","5000"));
        BigDecimal platinum = new BigDecimal(params.getOrDefault("platinumMinSpend","10000"));
        BigDecimal silver = new BigDecimal(params.getOrDefault("silverMinSpend","1000"));

        BigDecimal monthlyOrderCount = stats != null ? stats.getMonthlyOrderValue() : BigDecimal.ZERO;
        if(monthlyOrderCount.compareTo(silver) >= 0) return  Tier.SILVER;
        if(monthlyOrderCount.compareTo(gold)>= 0)  return Tier.GOLD;
        if(monthlyOrderCount.compareTo(platinum)>=0) return Tier.PLATINUM;
        return null;
    }

    @Override
    public String name() {
        return "MonthlySpendRule";
    }
}
