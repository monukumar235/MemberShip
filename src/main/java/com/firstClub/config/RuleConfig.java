package com.firstClub.config;

import com.firstClub.rule.CohortRule;
import com.firstClub.rule.MonthlySpendRule;
import com.firstClub.rule.OrderCountRule;
import com.firstClub.rule.TierRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RuleConfig {
    @Bean
    public List<TierRule> tierRules(){
        return List.of(new OrderCountRule(),new CohortRule(),new MonthlySpendRule());
    }
}
