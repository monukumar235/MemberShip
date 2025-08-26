package com.firstClub.dto;

import com.firstClub.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberShipSnapShot {
    private Long userId;
    private String status;
    private String tier;
    private String period;
    private LocalDate startDate;
    private LocalDate endDate;

    public MemberShipSnapShot(Subscription subscription) {
        this.userId = subscription.getUserId();
        this.status = subscription.getStatus().name();
        this.tier = subscription.getTier().name();
        this.period = subscription.getPeriod().name();
        this.startDate = subscription.getStartDate();
        this.endDate = subscription.getEndDate();
    }
}
