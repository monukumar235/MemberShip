package com.firstClub.comman;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ApplicationProperties {
    @Value("${tier.silver.minOrders}")
    private String silverMinOrders;

    @Value("${tier.gold.minOrders}")
    private String goldMinOrders;

    @Value("${tier.platinum.minOrders}")
    private String platinumMinOrders;

    @Value("${tier.gold.minSpend}")
    private String goldMinSpend;

    @Value("${tier.platinum.minSpend}")
    private String platinumMinSpend;

    @Value("${tier.gold.cohorts}")
    private String goldCohorts;

    @Value("${tier.platinum.cohorts}")
    private String platinumCohorts;
}
