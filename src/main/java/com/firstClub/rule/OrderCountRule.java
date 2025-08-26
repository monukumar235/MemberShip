package com.firstClub.rule;

import com.firstClub.constants.Tier;
import com.firstClub.entity.OrderStats;

import java.util.Map;

public class OrderCountRule implements TierRule{
    @Override
    public Tier evaluate(Long userId, OrderStats stats, Map<String,String> params) {

        int silver = Integer.parseInt(params.getOrDefault("silverMinOrders","3"));
        int gold = Integer.parseInt(params.getOrDefault("goldMinOrders","6"));
        int platinum = Integer.parseInt(params.getOrDefault("platinumMinOrders","10"));

        int c = stats !=null ? stats.getMonthlyOrderCount() : 0;
        if(c>= platinum) return Tier.PLATINUM;
        if(c>=gold) return Tier.GOLD;
        if(c>=silver) return Tier.SILVER;

        return null;
    }

    @Override
    public String name() {
        return "OrderCountRule";
    }
}
