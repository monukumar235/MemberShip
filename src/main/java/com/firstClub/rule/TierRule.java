package com.firstClub.rule;

import com.firstClub.constants.Tier;
import com.firstClub.entity.OrderStats;

import java.util.Map;

public interface TierRule {
    Tier evaluate(Long userId, OrderStats stats, Map<String,String> param);
    String name();
}
