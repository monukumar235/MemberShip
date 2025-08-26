package com.firstClub.config;

import com.firstClub.constants.PlanPeriod;
import com.firstClub.constants.Tier;
import com.firstClub.entity.OrderStats;
import com.firstClub.entity.PlanPricing;
import com.firstClub.repository.OrderStatsRepo;
import com.firstClub.repository.PlanPricingRepo;
import com.firstClub.service.SubcriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;


@Configuration
@EnableScheduling
public class DemoDataConfig {

    @Autowired
    private SubcriptionService service;
    @Bean
    CommandLineRunner seed(PlanPricingRepo planRepo, OrderStatsRepo statsRepo) {
        return args -> {
            if (planRepo.count() == 0) {

                planRepo.save(new PlanPricing(PlanPeriod.MONTHLY, Tier.SILVER, new BigDecimal("149")));
                planRepo.save(new PlanPricing(PlanPeriod.MONTHLY, Tier.GOLD, new BigDecimal("249")));
                planRepo.save(new PlanPricing(PlanPeriod.MONTHLY, Tier.PLATINUM, new BigDecimal("399")));

                planRepo.save(new PlanPricing(PlanPeriod.QUARTERLY, Tier.SILVER, new BigDecimal("399")));
                planRepo.save(new PlanPricing(PlanPeriod.QUARTERLY, Tier.GOLD, new BigDecimal("699")));
                planRepo.save(new PlanPricing(PlanPeriod.QUARTERLY, Tier.PLATINUM, new BigDecimal("1099")));

                planRepo.save(new PlanPricing(PlanPeriod.YEARLY, Tier.SILVER, new BigDecimal("1299")));
                planRepo.save(new PlanPricing(PlanPeriod.YEARLY, Tier.GOLD, new BigDecimal("2199")));
                planRepo.save(new PlanPricing(PlanPeriod.YEARLY, Tier.PLATINUM, new BigDecimal("3499")));
            }


            if (statsRepo.count() == 0) {
                statsRepo.save(new OrderStats(1L, 2, new BigDecimal("1200"), "STUDENT"));
                statsRepo.save(new OrderStats(2L, 7, new BigDecimal("6500"), null));
                statsRepo.save(new OrderStats(3L, 12, new BigDecimal("15000"), "HNI"));
            }
        };
    }

    @Scheduled(fixedDelay = 60_000)
    public void expireTask(){
        service.expireDue();
    }

}
