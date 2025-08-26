package com.firstClub.service;

import com.firstClub.constants.PlanPeriod;
import com.firstClub.constants.SubscriptionStatus;
import com.firstClub.constants.Tier;
import com.firstClub.dto.MemberShipSnapShot;
import com.firstClub.dto.SubscriptionRequest;
import com.firstClub.dto.SubscriptionView;
import com.firstClub.dto.UpdateTierRequest;
import com.firstClub.entity.OrderStats;
import com.firstClub.entity.PlanPricing;
import com.firstClub.entity.Subscription;
import com.firstClub.exception.BadRequest;
import com.firstClub.exception.NotFound;
import com.firstClub.repository.OrderStatsRepo;
import com.firstClub.repository.SubscriptionRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class SubcriptionService {

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Autowired
    private TierService tierService;

    @Autowired
    private PlanService planService;

    @Autowired
    private OrderStatsRepo orderStatsRepo;

    @Transactional
    public SubscriptionView subscribe(SubscriptionRequest request) {

        if(request.getTier()== null){
            request.setTier(tierService.computeTier(request.getUserId()));
        }

        planService.requiredPlan(request.getPeriod(), request.getTier());
        Subscription s = new Subscription();
        s.setUserId(request.getUserId());
        s.setPeriod(request.getPeriod());
        s.setTier(request.getTier());
        s.setStartDate(LocalDate.now());
        s.setStatus(SubscriptionStatus.ACTIVE);
        s.setEndDate(addPeriod(LocalDate.now(),request.getPeriod()));
        subscriptionRepo.save(s);

        return new SubscriptionView(s);

    }

    private LocalDate addPeriod(LocalDate start, PlanPeriod period) {
        return switch (period){
            case MONTHLY -> start.plusMonths(1);
            case QUARTERLY -> start.plusMonths(3);
            case YEARLY ->  start.plusYears(1);
        };
    }

    public MemberShipSnapShot activeSubs(Long userId){
        List<Subscription> activeByUserId = subscriptionRepo.findByUserIdAndStatus(userId,SubscriptionStatus.ACTIVE);
        Subscription subscription = activeByUserId.stream().findFirst().orElse(null);
        if(subscription == null){
            return new MemberShipSnapShot(userId,"None",null,null,null,null);
        }
        return new MemberShipSnapShot(subscription);
    }

    @Transactional
    public SubscriptionView upgrade(Long subscriptionId, UpdateTierRequest request) {
        Subscription subscription = subscriptionRepo.findById(subscriptionId).orElseThrow(()-> new NotFound("Subscription not found!"));
        requireActive(subscription);
        if(request.getTier().ordinal() <= subscription.getTier().ordinal()){
            throw new BadRequest("Upgrade requires higher tier!");
        }
        planService.requiredPlan(subscription.getPeriod(),request.getTier());
        subscription.setTier(request.getTier());
        return new SubscriptionView(subscription);
    }

    private void requireActive(Subscription s){
        if(s.getStatus()!=SubscriptionStatus.ACTIVE) {
            throw new BadRequest("Subscription is not active!");
        };
    }
    @Transactional
    public SubscriptionView downgrade(Long subscriptionId, UpdateTierRequest request) {
        Subscription subscription = subscriptionRepo.findById(subscriptionId).orElseThrow(()->new NotFound("Subscription not found!"));
        requireActive(subscription);
        if(request.getTier().ordinal()>=subscription.getTier().ordinal()){
            throw new BadRequest("Downgrade requires lower tier!");
        }
        planService.requiredPlan(subscription.getPeriod(),request.getTier());
        subscription.setTier(request.getTier());
        return new SubscriptionView(subscription);
    }

    @Transactional
    public SubscriptionView cancel(Long subscriptionId) {
        Subscription subscription = subscriptionRepo.findById(subscriptionId).orElseThrow(()->new NotFound("Subscription not found!"));
        if(subscription.getStatus()!=SubscriptionStatus.ACTIVE) return new SubscriptionView(subscription);
        subscription.setStatus(SubscriptionStatus.CANCELLED);
        return new SubscriptionView(subscription);
    }

    public void insertStats(Long userId, int monthlyOrders, BigDecimal monthlyValue, String cohort) {
        OrderStats orderStats = orderStatsRepo.findByUserId(userId).orElse(new OrderStats(userId, 0, BigDecimal.ZERO, null));
        orderStats.setMonthlyOrderCount(monthlyOrders);
        orderStats.setMonthlyOrderValue(monthlyValue);
        orderStats.setCohort(cohort);

        orderStatsRepo.save(orderStats);
    }

    public Map<String,Object> recommended(Long userId) {
        Tier tier = tierService.computeTier(userId);
        return tier!=null ? Map.of("userId",userId,"recommendedTier",tier.name()) : null;
    }

    @Transactional
    public  int expireDue(){
        LocalDate today = LocalDate.now();
        List<Subscription> subscriptions = subscriptionRepo.findAll();
        int changed =0;
        for(Subscription subscription : subscriptions){
            if(subscription.getStatus() == SubscriptionStatus.ACTIVE && !today.isBefore(subscription.getEndDate())){
                subscription.setStatus(SubscriptionStatus.EXPIRED);
                changed++;
            }
        }
        return changed;
    }
}
