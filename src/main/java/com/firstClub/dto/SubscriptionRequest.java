package com.firstClub.dto;

import com.firstClub.constants.PlanPeriod;
import com.firstClub.constants.Tier;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionRequest {
    @NotNull
    private Long userId;
    @NotNull
    private PlanPeriod period;
    private Tier tier;

}
