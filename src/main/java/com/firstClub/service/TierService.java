package com.firstClub.service;

import com.firstClub.comman.ApplicationProperties;
import com.firstClub.constants.Tier;
import com.firstClub.entity.OrderStats;
import com.firstClub.exception.NotFound;
import com.firstClub.repository.OrderStatsRepo;
import com.firstClub.rule.TierRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TierService {

    @Autowired
    private OrderStatsRepo orderStatsRepo;
    @Autowired
    private List<TierRule> tierRules;

    @Autowired
    private ApplicationProperties applicationProperties;

    public Tier computeTier(Long userId){
        OrderStats stats = orderStatsRepo.findByUserId(userId).orElseThrow(()-> new NotFound("Order with userId  not found! " + userId));

        HashMap<String,String> params = new HashMap<>();
        params.put("silverMinOrders",applicationProperties.getSilverMinOrders());
        params.put("goldMinOrders",applicationProperties.getGoldMinOrders());
        params.put("goldMinSpend",applicationProperties.getGoldMinSpend());
        params.put("platinumMinSpend",applicationProperties.getPlatinumMinSpend());
        params.put("goldCohorts",applicationProperties.getGoldCohorts());
        params.put("platinumCohorts",applicationProperties.getPlatinumCohorts());
        Tier tier= null;
        for(TierRule rule : tierRules){
           tier =  rule.evaluate(userId,stats,params);
           if(tier!=null) return tier;
        }
        return tier;
    }


}
