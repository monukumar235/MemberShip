package com.firstClub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firstClub.constants.PlanPeriod;
import com.firstClub.constants.Tier;
import com.firstClub.entity.Subscription;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionView {
    @JsonProperty("subscriptionId")
    private Long id;
    private Long userId;
    private String period;
    private String tier;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;

    public SubscriptionView(Subscription s) {
        this.id = s.getId();
        this.userId = s.getUserId();
        this.period = s.getPeriod().toString();
        this.tier = s.getTier().toString();
        this.status = s.getStatus().toString();
        this.startDate = s.getStartDate();
        this.endDate = s.getEndDate();

    }
}
