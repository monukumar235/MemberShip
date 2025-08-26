package com.firstClub.service;

import com.firstClub.constants.PlanPeriod;
import com.firstClub.constants.Tier;
import com.firstClub.dto.PlanDto;
import com.firstClub.entity.PlanPricing;
import com.firstClub.exception.NotFound;
import com.firstClub.repository.PlanPricingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ssl.NoSuchSslBundleException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanPricingRepo planPricingRepo;
    public List<PlanDto> listPlans() {
        List<PlanDto> planDtos = new ArrayList<>();
        List<PlanPricing> planPricingList = planPricingRepo.findAllByOrderByPeriodAscTierAsc();
        if(planPricingList.isEmpty()){
            throw new NotFound("Plan pricing not found!");
        }
        for(PlanPricing planPricing : planPricingList){
            PlanDto planDto = new PlanDto(planPricing);
            planDtos.add(planDto);
        }
        return planDtos;
    }

    public PlanPricing requiredPlan(PlanPeriod planPeriod, Tier tier)
    {
        return planPricingRepo.findByPeriodAndTier(planPeriod,tier).orElseThrow(()->new NotFound("Plan pricing not found!"));
    }
}
