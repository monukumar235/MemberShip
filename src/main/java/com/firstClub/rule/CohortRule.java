package com.firstClub.rule;

import com.firstClub.constants.Tier;
import com.firstClub.entity.OrderStats;

import java.util.Arrays;
import java.util.Map;

public class CohortRule implements TierRule{
    @Override
    public Tier evaluate(Long userId, OrderStats stats, Map<String, String> param) {
        String cohort = stats!=null ? stats.getCohort() : null;
        if(cohort == null) return null;
        if(Arrays.asList(param.getOrDefault("platinumCohorts","").split(",")).contains(cohort)) return Tier.PLATINUM;
        if(Arrays.asList(param.getOrDefault("goldCohorts","STUDENT").split(",")).contains(cohort)) return Tier.GOLD;
        return null;
    }

    @Override
    public String name() {
        return "CohortRule";
    }
}
